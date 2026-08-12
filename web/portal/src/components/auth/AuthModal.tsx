/** Author: Charlie */

import { useEffect, useMemo, useRef, useState } from 'react'
import {
  Button,
  Checkbox,
  ConfigProvider,
  Form,
  Input,
  Modal,
  Result,
  Space,
  Tabs,
  message,
} from 'antd'
import { CloseOutlined } from '@ant-design/icons'
import { Link, useNavigate } from 'react-router-dom'
import { authApi } from '@/api'
import { CaptchaInput, type CaptchaInputHandle } from '@/components/common/CaptchaInput'
import { PasswordStrength } from '@/components/common/PasswordStrength'
import { useAuthModalStore } from '@/stores/authModal'
import { useAuthStore } from '@/stores/auth'
import { encryptPasswords } from '@/utils/security'
import { isValidEmail, isValidPhone } from '@/utils/validate'
import { wireBool } from '@/utils/wire'
import '@/pages/auth/auth-page.css'
import './auth-modal.css'

const brandName = import.meta.env.VITE_APP_TITLE || 'HEI'

const OTP_COOLDOWN_SECONDS = 60

type LoginType = 'ACCOUNT' | 'EMAIL' | 'PHONE'

type LoginFormValues = {
  account?: string
  email?: string
  phone?: string
  password?: string
  otp_code?: string
  captcha_id: string
  captcha_value: string
  remember: boolean
}

type RegisterFormValues = {
  account?: string
  email?: string
  phone?: string
  otp_code?: string
  password: string
  confirmPassword: string
  captcha_id: string
  captcha_value: string
}

const allTabItems = [
  { key: 'ACCOUNT', label: '账号', placeholder: '请输入账号' },
  { key: 'EMAIL', label: '邮箱', placeholder: '请输入登录邮箱' },
  { key: 'PHONE', label: '手机号', placeholder: '请输入登录手机号' },
]

const registerTabItems = [
  { key: 'ACCOUNT', label: '用户名', placeholder: '请输入用户名' },
  { key: 'EMAIL', label: '邮箱', placeholder: '请输入注册邮箱' },
  { key: 'PHONE', label: '手机号', placeholder: '请输入注册手机号' },
]

export function AuthModal() {
  const mode = useAuthModalStore((s) => s.mode)
  const redirect = useAuthModalStore((s) => s.redirect)
  const close = useAuthModalStore((s) => s.close)
  const switchMode = useAuthModalStore((s) => s.switchMode)
  const navigate = useNavigate()
  const login = useAuthStore((s) => s.login)

  const [loginForm] = Form.useForm<LoginFormValues>()
  const [registerForm] = Form.useForm<RegisterFormValues>()
  const [activeType, setActiveType] = useState<LoginType>('ACCOUNT')
  const [registerChannel, setRegisterChannel] = useState<LoginType>('ACCOUNT')
  const [loginMode, setLoginMode] = useState<'PASSWORD' | 'OTP'>('PASSWORD')
  const [loading, setLoading] = useState(false)
  const [sendingCode, setSendingCode] = useState(false)
  const [sendingRegisterCode, setSendingRegisterCode] = useState(false)
  const [otpCooldown, setOtpCooldown] = useState(0)
  const [registerOtpCooldown, setRegisterOtpCooldown] = useState(0)
  const [registerEnabled, setRegisterEnabled] = useState(true)
  const [options, setOptions] = useState({
    allow_account: true,
    allow_email: true,
    allow_phone: true,
    allow_otp: true,
    register_allow_account: true,
    register_allow_email: true,
    register_allow_phone: false,
  })
  const [oauthProviders, setOauthProviders] = useState<
    Array<{ provider: string; label: string; enabled: boolean; web_oauth: boolean }>
  >([])
  const [oauthLoading, setOauthLoading] = useState<string | null>(null)
  const loginCaptchaRef = useRef<CaptchaInputHandle>(null)
  const registerCaptchaRef = useRef<CaptchaInputHandle>(null)
  const loginCaptchaId = Form.useWatch('captcha_id', loginForm) || ''
  const loginCaptchaValue = Form.useWatch('captcha_value', loginForm) || ''
  const registerCaptchaId = Form.useWatch('captcha_id', registerForm) || ''
  const registerCaptchaValue = Form.useWatch('captcha_value', registerForm) || ''
  const password = Form.useWatch('password', registerForm) || ''

  const open = mode !== null
  const isLogin = mode === 'login'

  const tabItems = useMemo(
    () =>
      allTabItems.filter((item) => {
        if (item.key === 'ACCOUNT') return options.allow_account
        if (item.key === 'EMAIL') return options.allow_email
        return options.allow_phone
      }),
    [options],
  )

  const registerTabs = useMemo(
    () =>
      registerTabItems.filter((item) => {
        if (item.key === 'ACCOUNT') return options.register_allow_account
        if (item.key === 'EMAIL') return options.register_allow_email
        return options.register_allow_phone
      }),
    [options],
  )

  const resolvedActiveType = tabItems.some((item) => item.key === activeType)
    ? activeType
    : (tabItems[0]?.key as LoginType) || 'ACCOUNT'

  const resolvedRegisterChannel = registerTabs.some((item) => item.key === registerChannel)
    ? registerChannel
    : (registerTabs[0]?.key as LoginType) || 'ACCOUNT'

  const otpAvailable =
    options.allow_otp && (resolvedActiveType === 'EMAIL' || resolvedActiveType === 'PHONE')

  const resolvedLoginMode = otpAvailable ? loginMode : 'PASSWORD'

  useEffect(() => {
    if (!open) return
    void authApi
      .authOptions()
      .then((res) => {
        const data = res?.data || {}
        setOptions({
          allow_account: wireBool(data.allow_account ?? true),
          allow_email: wireBool(data.allow_email ?? true),
          allow_phone: wireBool(data.allow_phone ?? true),
          allow_otp: wireBool(data.allow_otp ?? true),
          register_allow_account: wireBool(data.register_allow_account ?? true),
          register_allow_email: wireBool(data.register_allow_email ?? true),
          register_allow_phone: wireBool(data.register_allow_phone ?? false),
        })
        setRegisterEnabled(wireBool(data.register_enabled ?? false))
        const providers = Array.isArray(data.oauth_providers) ? data.oauth_providers : []
        setOauthProviders(
          providers
            .map((item: any) => ({
              provider: String(item.provider || ''),
              label: String(item.label || item.provider || ''),
              enabled: wireBool(item.enabled ?? false),
              web_oauth: wireBool(item.web_oauth ?? true),
            }))
            .filter((item: { provider: string; enabled: boolean; web_oauth: boolean }) =>
              Boolean(item.provider && item.enabled && item.web_oauth),
            ),
        )
      })
      .catch(() => undefined)
  }, [open])

  useEffect(() => {
    if (otpCooldown <= 0) return
    const timer = window.setTimeout(() => setOtpCooldown((v) => v - 1), 1000)
    return () => window.clearTimeout(timer)
  }, [otpCooldown])

  useEffect(() => {
    if (registerOtpCooldown <= 0) return
    const timer = window.setTimeout(() => setRegisterOtpCooldown((v) => v - 1), 1000)
    return () => window.clearTimeout(timer)
  }, [registerOtpCooldown])

  async function onOauthLogin(provider: string) {
    if (oauthLoading) return
    setOauthLoading(provider)
    try {
      const res = await authApi.oauthAuthorize(provider, {
        intent: 'LOGIN',
        redirect: redirect || undefined,
      })
      const url = res?.data?.authorize_url
      if (!url) {
        message.error('无法发起三方登录')
        return
      }
      window.location.href = String(url)
    } catch {
      // 全局错误提示
    } finally {
      setOauthLoading(null)
    }
  }

  function resetForms() {
    loginForm.resetFields()
    registerForm.resetFields()
    setLoading(false)
    setOtpCooldown(0)
    setRegisterOtpCooldown(0)
    setLoginMode('PASSWORD')
    setActiveType('ACCOUNT')
    setRegisterChannel('ACCOUNT')
  }

  async function onSendCode() {
    if (otpCooldown > 0 || sendingCode) return
    const identity =
      resolvedActiveType === 'ACCOUNT'
        ? loginForm.getFieldValue('account')?.trim()
        : resolvedActiveType === 'EMAIL'
          ? loginForm.getFieldValue('email')?.trim()
          : loginForm.getFieldValue('phone')?.trim()
    if (!identity) {
      message.warning(`请输入${tabItems.find((t) => t.key === resolvedActiveType)?.label}`)
      return
    }
    if (resolvedActiveType === 'EMAIL' && !isValidEmail(identity)) {
      message.warning('请输入有效邮箱')
      return
    }
    if (resolvedActiveType === 'PHONE' && !isValidPhone(identity)) {
      message.warning('请输入有效手机号')
      return
    }
    if (!loginCaptchaValue.trim()) {
      message.warning('请输入图形验证码')
      return
    }
    setSendingCode(true)
    try {
      await authApi.sendLoginCode({
        target: identity,
        channel: resolvedActiveType === 'EMAIL' ? 'EMAIL' : 'PHONE',
        captcha_id: loginCaptchaId,
        captcha_value: loginCaptchaValue,
      })
      message.success('验证码已发送，请查收后填写')
      setOtpCooldown(OTP_COOLDOWN_SECONDS)
      await loginCaptchaRef.current?.refresh()
    } catch {
      await loginCaptchaRef.current?.refresh()
    } finally {
      setSendingCode(false)
    }
  }

  async function onLoginFinish(values: LoginFormValues) {
    const identity =
      resolvedActiveType === 'ACCOUNT'
        ? values.account?.trim()
        : resolvedActiveType === 'EMAIL'
          ? values.email?.trim()
          : values.phone?.trim()

    if (!identity) {
      message.warning(`请输入${tabItems.find((t) => t.key === resolvedActiveType)?.label}`)
      return
    }
    if (resolvedActiveType === 'EMAIL' && !isValidEmail(identity)) {
      message.warning('请输入有效邮箱')
      return
    }
    if (resolvedActiveType === 'PHONE' && !isValidPhone(identity)) {
      message.warning('请输入有效手机号')
      return
    }

    setLoading(true)
    try {
      let password = ''
      let passwordKeyId: string | undefined
      if (resolvedLoginMode === 'PASSWORD') {
        const encrypted = await encryptPasswords({ password: values.password || '' })
        password = encrypted.values.password || ''
        passwordKeyId = encrypted.password_key_id
      }
      const next = await login(identity, password, redirect, values.remember, resolvedActiveType, {
        password_key_id: passwordKeyId,
        captcha_id: values.captcha_id,
        captcha_value: values.captcha_value,
        login_mode: resolvedLoginMode,
        ...(resolvedLoginMode === 'OTP' && values.otp_code?.trim()
          ? { otp_code: values.otp_code.trim() }
          : {}),
      })
      message.success('登录成功')
      close()
      navigate(next)
    } catch {
      await loginCaptchaRef.current?.refresh()
    } finally {
      setLoading(false)
    }
  }

  async function onSendRegisterCode() {
    if (registerOtpCooldown > 0 || sendingRegisterCode) return
    if (resolvedRegisterChannel !== 'EMAIL' && resolvedRegisterChannel !== 'PHONE') return
    const identity =
      resolvedRegisterChannel === 'EMAIL'
        ? registerForm.getFieldValue('email')?.trim()
        : registerForm.getFieldValue('phone')?.trim()
    if (!identity) {
      message.warning(`请输入${registerTabs.find((t) => t.key === resolvedRegisterChannel)?.label}`)
      return
    }
    if (resolvedRegisterChannel === 'EMAIL' && !isValidEmail(identity)) {
      message.warning('请输入有效邮箱')
      return
    }
    if (resolvedRegisterChannel === 'PHONE' && !isValidPhone(identity)) {
      message.warning('请输入有效手机号')
      return
    }
    if (!registerCaptchaValue.trim()) {
      message.warning('请输入图形验证码')
      return
    }
    setSendingRegisterCode(true)
    try {
      await authApi.sendRegisterCode({
        target: identity,
        channel: resolvedRegisterChannel,
        captcha_id: registerCaptchaId,
        captcha_value: registerCaptchaValue,
      })
      message.success('验证码已发送，请查收后填写')
      setRegisterOtpCooldown(OTP_COOLDOWN_SECONDS)
      await registerCaptchaRef.current?.refresh()
    } catch {
      await registerCaptchaRef.current?.refresh()
    } finally {
      setSendingRegisterCode(false)
    }
  }

  async function onRegisterFinish(values: RegisterFormValues) {
    const encryptedPayload: Record<string, unknown> = {
      register_channel: resolvedRegisterChannel,
      captcha_id: values.captcha_id,
      captcha_value: values.captcha_value,
    }
    if (resolvedRegisterChannel === 'ACCOUNT') {
      const account = (values.account || '').trim()
      if (account.length < 3 || account.length > 64) {
        message.warning('用户名需 3-64 个字符')
        return
      }
      encryptedPayload.account = account
    } else if (resolvedRegisterChannel === 'EMAIL') {
      const email = (values.email || '').trim()
      if (!isValidEmail(email) || email.length > 128) {
        message.warning('邮箱格式不正确')
        return
      }
      if (!values.otp_code?.trim()) {
        message.warning('请输入邮箱验证码')
        return
      }
      encryptedPayload.email = email
      encryptedPayload.otp_code = values.otp_code.trim()
    } else {
      const phone = (values.phone || '').trim()
      if (!isValidPhone(phone)) {
        message.warning('请输入有效手机号')
        return
      }
      if (!values.otp_code?.trim()) {
        message.warning('请输入手机验证码')
        return
      }
      encryptedPayload.phone = phone
      encryptedPayload.otp_code = values.otp_code.trim()
    }

    setLoading(true)
    try {
      const encrypted = await encryptPasswords({ password: values.password })
      await authApi.register({
        ...encryptedPayload,
        password: encrypted.values.password || '',
        password_key_id: encrypted.password_key_id,
      })
      message.success('注册成功，请登录')
      registerForm.resetFields()
      switchMode('login')
    } catch {
      await registerCaptchaRef.current?.refresh()
    } finally {
      setLoading(false)
    }
  }

  const activeField = resolvedActiveType.toLowerCase() as 'account' | 'email' | 'phone'
  const title = isLogin ? '欢迎登录' : '注册账号'
  const headerExtra = isLogin ? (
    registerEnabled ? (
      <button type="button" className="linkish" onClick={() => switchMode('register')}>
        没有账号？去注册
      </button>
    ) : null
  ) : (
    <button type="button" className="linkish" onClick={() => switchMode('login')}>
      已有账号？去登录
    </button>
  )

  return (
    <Modal
      open={open}
      onCancel={close}
      afterOpenChange={(next) => {
        if (!next) resetForms()
      }}
      footer={null}
      width={880}
      centered
      destroyOnHidden
      maskClosable
      keyboard
      className="auth-modal"
      closeIcon={<CloseOutlined />}
      styles={{
        container: { padding: 0, overflow: 'hidden', background: 'transparent' },
        mask: { background: 'rgba(15, 23, 42, 0.45)' },
      }}
    >
      <div className="auth-modal__card auth-card">
        <aside className="auth-card__brand">
          <div className="auth-card__brand-deco" aria-hidden />
          <div className="auth-card__brand-inner">
            <Link to="/" className="auth-card__logo" onClick={close}>
              <span className="auth-card__logo-mark">{brandName.slice(0, 1).toUpperCase()}</span>
              <span className="auth-card__logo-text">{brandName}</span>
            </Link>
            <p className="auth-card__eyebrow">Portal</p>
            <h2 className="auth-card__headline">
              {isLogin ? '登录门户畅享更多服务' : '加入门户开启更多能力'}
            </h2>
            <p className="auth-card__lead">登录注册、个人中心与公告，开箱即用。</p>
            <div className="auth-card__brand-foot">
              <Link to="/" className="auth-card__brand-link" onClick={close}>
                进入门户首页
              </Link>
            </div>
          </div>
        </aside>

        <div className="auth-card__form">
          <div className="auth-card__form-head">
            <h1 className="auth-card__title">{title}</h1>
            {headerExtra ? <div className="auth-card__form-extra">{headerExtra}</div> : null}
          </div>

          <div className="auth-card__form-body">
            <ConfigProvider componentSize="large">
              {isLogin ? (
                <Form
                  form={loginForm}
                  layout="vertical"
                  requiredMark={false}
                  initialValues={{ remember: true, captcha_id: '', captcha_value: '' }}
                  onFinish={(v) => void onLoginFinish(v)}
                >
                  <Tabs
                    activeKey={resolvedActiveType}
                    items={tabItems.map((item) => ({ key: item.key, label: item.label }))}
                    onChange={(key) => setActiveType(key as LoginType)}
                  />

                  <Form.Item
                    name={activeField}
                    rules={[{ required: true, message: '请填写登录身份' }]}
                  >
                    <Input
                      placeholder={tabItems.find((t) => t.key === resolvedActiveType)?.placeholder}
                      allowClear
                    />
                  </Form.Item>

                  {resolvedLoginMode === 'PASSWORD' ? (
                    <Form.Item name="password" rules={[{ required: true, message: '请输入密码' }]}>
                      <Input.Password placeholder="请输入密码" />
                    </Form.Item>
                  ) : (
                    <Form.Item>
                      <Space.Compact block>
                        <Form.Item
                          name="otp_code"
                          noStyle
                          rules={[{ required: true, message: '请输入登录验证码' }]}
                        >
                          <Input placeholder="请输入登录验证码" />
                        </Form.Item>
                        <Button
                          loading={sendingCode}
                          disabled={otpCooldown > 0}
                          onClick={() => void onSendCode()}
                        >
                          {otpCooldown > 0 ? `${otpCooldown}s 后重发` : '发送验证码'}
                        </Button>
                      </Space.Compact>
                    </Form.Item>
                  )}

                  <Form.Item name="captcha_id" hidden>
                    <Input />
                  </Form.Item>

                  <Form.Item
                    name="captcha_value"
                    rules={[{ required: true, message: '请输入验证码' }]}
                  >
                    <CaptchaInput
                      ref={loginCaptchaRef}
                      size="large"
                      onCaptchaIdChange={(v) => loginForm.setFieldValue('captcha_id', v)}
                    />
                  </Form.Item>

                  <Form.Item>
                    <div className="flex items-center justify-between">
                      <Form.Item name="remember" valuePropName="checked" noStyle>
                        <Checkbox>记住我</Checkbox>
                      </Form.Item>
                      <div className="auth-form-row__links">
                        {otpAvailable ? (
                          <>
                            <button
                              type="button"
                              className="auth-mode-link"
                              onClick={() =>
                                setLoginMode(resolvedLoginMode === 'PASSWORD' ? 'OTP' : 'PASSWORD')
                              }
                            >
                              {resolvedLoginMode === 'PASSWORD' ? '验证码登录' : '密码登录'}
                            </button>
                            <span className="auth-form-row__sep">·</span>
                          </>
                        ) : null}
                        <Link to="/auth/forgot-password" onClick={close}>
                          忘记密码？
                        </Link>
                      </div>
                    </div>
                  </Form.Item>

                  <Form.Item>
                    <Button type="primary" htmlType="submit" block loading={loading}>
                      登录
                    </Button>
                  </Form.Item>

                  {oauthProviders.length > 0 ? (
                    <div className="auth-oauth">
                      <div className="auth-oauth__divider">
                        <span>其他登录方式</span>
                      </div>
                      <div className="auth-oauth__row">
                        {oauthProviders.map((item) => (
                          <Button
                            key={item.provider}
                            className="auth-oauth__btn"
                            loading={oauthLoading === item.provider}
                            disabled={Boolean(oauthLoading)}
                            onClick={() => void onOauthLogin(item.provider)}
                          >
                            {item.label}
                          </Button>
                        ))}
                      </div>
                    </div>
                  ) : null}
                </Form>
              ) : !registerEnabled || registerTabs.length === 0 ? (
                <Result
                  status="info"
                  title="暂未开放注册"
                  extra={
                    <Button type="primary" onClick={() => switchMode('login')}>
                      返回登录
                    </Button>
                  }
                />
              ) : (
                <Form
                  form={registerForm}
                  layout="vertical"
                  requiredMark={false}
                  initialValues={{ captcha_id: '', captcha_value: '' }}
                  onFinish={(v) => void onRegisterFinish(v)}
                >
                  <Tabs
                    activeKey={resolvedRegisterChannel}
                    items={registerTabs.map((item) => ({ key: item.key, label: item.label }))}
                    onChange={(key) => setRegisterChannel(key as LoginType)}
                  />

                  {resolvedRegisterChannel === 'ACCOUNT' ? (
                    <Form.Item
                      name="account"
                      rules={[
                        { required: true, message: '请输入用户名' },
                        { min: 3, max: 64, message: '用户名需 3-64 个字符' },
                      ]}
                    >
                      <Input placeholder="用户名" allowClear />
                    </Form.Item>
                  ) : null}

                  {resolvedRegisterChannel === 'EMAIL' ? (
                    <>
                      <Form.Item
                        name="email"
                        rules={[
                          { required: true, message: '请输入邮箱' },
                          { type: 'email', message: '邮箱格式不正确' },
                          { max: 128, message: '邮箱最多 128 个字符' },
                        ]}
                      >
                        <Input placeholder="邮箱" allowClear />
                      </Form.Item>
                      <Form.Item>
                        <Space.Compact block>
                          <Form.Item
                            name="otp_code"
                            noStyle
                            rules={[{ required: true, message: '请输入邮箱验证码' }]}
                          >
                            <Input placeholder="邮箱验证码" />
                          </Form.Item>
                          <Button
                            loading={sendingRegisterCode}
                            disabled={registerOtpCooldown > 0}
                            onClick={() => void onSendRegisterCode()}
                          >
                            {registerOtpCooldown > 0 ? `${registerOtpCooldown}s 后重发` : '发送验证码'}
                          </Button>
                        </Space.Compact>
                      </Form.Item>
                    </>
                  ) : null}

                  {resolvedRegisterChannel === 'PHONE' ? (
                    <>
                      <Form.Item
                        name="phone"
                        rules={[{ required: true, message: '请输入手机号' }]}
                      >
                        <Input placeholder="手机号" allowClear />
                      </Form.Item>
                      <Form.Item>
                        <Space.Compact block>
                          <Form.Item
                            name="otp_code"
                            noStyle
                            rules={[{ required: true, message: '请输入手机验证码' }]}
                          >
                            <Input placeholder="手机验证码" />
                          </Form.Item>
                          <Button
                            loading={sendingRegisterCode}
                            disabled={registerOtpCooldown > 0}
                            onClick={() => void onSendRegisterCode()}
                          >
                            {registerOtpCooldown > 0 ? `${registerOtpCooldown}s 后重发` : '发送验证码'}
                          </Button>
                        </Space.Compact>
                      </Form.Item>
                    </>
                  ) : null}

                  <Form.Item name="password" rules={[{ required: true, message: '请输入密码' }]}>
                    <Input.Password placeholder="密码" />
                  </Form.Item>
                  <PasswordStrength password={password} />

                  <Form.Item
                    name="confirmPassword"
                    dependencies={['password']}
                    rules={[
                      { required: true, message: '请确认密码' },
                      ({ getFieldValue }) => ({
                        validator(_, value) {
                          if (!value || getFieldValue('password') === value) {
                            return Promise.resolve()
                          }
                          return Promise.reject(new Error('两次密码输入不一致'))
                        },
                      }),
                    ]}
                  >
                    <Input.Password placeholder="确认密码" />
                  </Form.Item>

                  <Form.Item
                    name="captcha_value"
                    rules={[{ required: true, message: '请输入验证码' }]}
                  >
                    <CaptchaInput
                      ref={registerCaptchaRef}
                      size="large"
                      onCaptchaIdChange={(v) => registerForm.setFieldValue('captcha_id', v)}
                    />
                  </Form.Item>

                  <Form.Item name="captcha_id" hidden>
                    <Input />
                  </Form.Item>

                  <Form.Item>
                    <Button type="primary" htmlType="submit" block loading={loading}>
                      立即注册
                    </Button>
                  </Form.Item>
                </Form>
              )}
            </ConfigProvider>
          </div>

          <div className="auth-modal__footer">注册登录即表示同意相关服务条款与隐私政策</div>
        </div>
      </div>
    </Modal>
  )
}
