/** Author: Charlie */

import { useEffect, useState } from 'react'
import { Result, Spin, message } from 'antd'
import { useNavigate, useSearchParams } from 'react-router-dom'
import { useAuthStore } from '@/stores/auth'
import { clearToken, setToken } from '@/utils/session'
import { clearAuthStorage } from '@/utils/storage'
import { getSafeRedirect } from '@/utils/validate'
import { wireBool } from '@/utils/wire'
import { refreshDict, syncDictTree } from '@/utils/dict'

/**
 * OAuth 前端回调页：接收后端 302 的 query，写 token 并完成会话。
 */
export function OAuthCallbackPage() {
  const [searchParams] = useSearchParams()
  const navigate = useNavigate()
  const refreshUserInfo = useAuthStore((s) => s.refreshUserInfo)
  const resolveSecurityRedirect = useAuthStore((s) => s.resolveSecurityRedirect)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    let cancelled = false
    void (async () => {
      const status = searchParams.get('oauth_status')
      const action = searchParams.get('oauth_action') || ''
      const rawMessage = searchParams.get('oauth_message')
      const token = searchParams.get('token')
      const redirect = searchParams.get('redirect')

      if (status !== 'ok') {
        let msg = '三方登录失败'
        if (rawMessage) {
          try {
            msg = decodeURIComponent(rawMessage)
          } catch {
            msg = rawMessage
          }
        }
        if (!cancelled) {
          setError(msg)
          message.error(msg)
          window.setTimeout(() => navigate('/auth/login', { replace: true }), 1600)
        }
        return
      }

      try {
        if (token) {
          clearToken()
          clearAuthStorage()
          setToken(token, true)
        }

        if (action === 'bound') {
          await refreshUserInfo()
          message.success('绑定成功')
          if (!cancelled) navigate('/usercenter?tab=oauth', { replace: true })
          return
        }

        const passwordExpired = wireBool(searchParams.get('password_expired') ?? false)
        const forceBindEmail = wireBool(searchParams.get('force_bind_email') ?? false)
        const forceBindPhone = wireBool(searchParams.get('force_bind_phone') ?? false)
        if (passwordExpired) {
          message.warning('密码已过期，请先修改密码')
        } else if (forceBindEmail || forceBindPhone) {
          message.warning('请先完成账号安全绑定')
        } else {
          message.success('登录成功')
        }

        await refreshUserInfo()
        syncDictTree()
        await refreshDict()
        const next = resolveSecurityRedirect(getSafeRedirect(redirect))
        if (!cancelled) navigate(next, { replace: true })
      } catch (e: any) {
        const msg = e?.message || '登录会话建立失败'
        if (!cancelled) {
          setError(msg)
          message.error(msg)
          window.setTimeout(() => navigate('/auth/login', { replace: true }), 1600)
        }
      }
    })()
    return () => {
      cancelled = true
    }
    // 仅首轮处理 query
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  if (error) {
    return (
      <div style={{ minHeight: '60vh', display: 'grid', placeItems: 'center', padding: 24 }}>
        <Result status="error" title="三方登录失败" subTitle={error} />
      </div>
    )
  }

  return (
    <div style={{ minHeight: '60vh', display: 'grid', placeItems: 'center' }}>
      <Spin size="large" tip="正在完成登录…" />
    </div>
  )
}
