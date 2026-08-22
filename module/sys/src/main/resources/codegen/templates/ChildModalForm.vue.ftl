<!--
  由 HEI 代码生成器生成。
  Author: ${plan.author}
  生成时间：${generated_at}
-->

<script setup lang="ts">
import type { FormInst, FormRules } from 'naive-ui'
import { ${api_export_name} } from '@/api'
<#assign _wires = []>
<#if target.has_form_bool><#assign _wires = _wires + ["wireBool"]></#if>
<#if target.has_form_int><#assign _wires = _wires + ["wireInt"]></#if>
<#if target.has_form_float><#assign _wires = _wires + ["wireFloat"]></#if>
<#if (_wires?size > 0)>
import { ${_wires?join(", ")} } from '@/utils/wire'
</#if>
import { createRequiredRule<#if target.has_form_datetime>, toApiDateTime, toFormDateTime</#if> } from '@/utils'
import { computed, reactive, ref } from 'vue'

const emit = defineEmits<{
  saved: []
}>()

const formRef = ref<FormInst | null>(null)
const defaultFormData: Record<string, any> = {
<#list target.form_fields as field>
  ${field.name}: ${field.vue_default},
</#list>
}
const state = reactive({
  showModal: false,
  loading: false,
  submitLoading: false,
  dataId: null as string | null,
  formModel: normalizeFormData(),
})

const modalTitle = computed(() => state.dataId ? '编辑${(plan.sub_business_name)?has_content?then(plan.sub_business_name, "明细")}' : '新增${(plan.sub_business_name)?has_content?then(plan.sub_business_name, "明细")}')
const rules = computed<FormRules>(() => ({
<#list target.form_fields as field><#if field.is_required || field.is_json>
  ${field.name}: [
<#if field.is_required>
<#if field.data_type == "bool">
    {
      validator: () => typeof state.formModel.${field.name} === 'boolean',
      message: '请选择${field.label}',
      trigger: 'change',
    },
<#elseif (field.data_type == "int" || field.data_type == "float")>
    {
      validator: () => typeof state.formModel.${field.name} === 'number' && Number.isFinite(state.formModel.${field.name}),
      message: '请输入${field.label}',
      trigger: ['input', 'blur'],
    },
<#else>
    createRequiredRule('${field.label}', <#if field.form_widget == "dict" || field.data_type == "bool" || field.is_datetime>'change'<#else>'input'</#if>),
</#if>
</#if>
<#if field.is_json>
    {
      validator: () => isValidJsonValue(state.formModel.${field.name}),
      message: '请输入合法 JSON 对象',
      trigger: ['input', 'blur'],
    },
</#if>
  ],
</#if></#list>
}))

async function openModal(id?: string, defaults: Partial<typeof defaultFormData> = {}) {
  state.dataId = id ?? null
  state.formModel = normalizeFormData(defaults)
  state.showModal = true
  if (id) {
    await fetchDetail(id)
  }
}

async function fetchDetail(id: string) {
  state.loading = true
  try {
    const response = await ${api_export_name}.childDetail({ id })
    state.formModel = normalizeFormData(response.data ?? {})
  } finally {
    state.loading = false
  }
}

function normalizeFormData(data: Record<string, any> = {}): Record<string, any> {
  return {
    ...defaultFormData,
    ...data,
<#list target.form_fields as field><#if field.is_bool>
    ${field.name}: data.${field.name} == null || data.${field.name} === '' ? defaultFormData.${field.name} : wireBool(String(data.${field.name})),
</#if></#list>
<#list target.form_fields as field><#if field.data_type == "int">
    ${field.name}: data.${field.name} == null || data.${field.name} === '' ? defaultFormData.${field.name} : wireInt(String(data.${field.name})),
</#if></#list>
<#list target.form_fields as field><#if field.data_type == "float">
    ${field.name}: data.${field.name} == null || data.${field.name} === '' ? defaultFormData.${field.name} : wireFloat(String(data.${field.name})),
</#if></#list>
<#list target.form_fields as field><#if field.is_datetime>
    ${field.name}: toFormDateTime(data.${field.name}),
</#if></#list>
<#list target.form_fields as field><#if field.is_json>
    ${field.name}: stringifyJsonValue(data.${field.name}),
</#if></#list>
  }
}
<#if target.needs_submit_normalize>

function normalizeSubmitData(data: Record<string, any>): Record<string, any> {
  return {
    ...data,
<#list target.form_fields as field><#if field.is_datetime>
    ${field.name}: toApiDateTime(data.${field.name}),
</#if></#list>
<#list target.form_fields as field><#if field.is_json>
    ${field.name}: parseJsonValue(data.${field.name}),
</#if></#list>
  }
}
</#if>
<#if target.has_form_json>

function parseJsonValue(value: unknown) {
  const text = String(value ?? '').trim()
  if (!text) {
    return {}
  }
  const parsed = JSON.parse(text)
  if (Array.isArray(parsed) || typeof parsed !== 'object' || parsed === null) {
    throw new Error('JSON value must be an object')
  }
  return parsed
}

function isValidJsonValue(value: unknown) {
  try {
    parseJsonValue(value)
    return true
  } catch {
    return false
  }
}

function stringifyJsonValue(value: unknown) {
  if (value === undefined || value === null || value === '') {
    return '{}'
  }
  if (typeof value === 'string') {
    try {
      return JSON.stringify(JSON.parse(value), null, 2)
    } catch {
      return value
    }
  }
  return JSON.stringify(value, null, 2)
}
</#if>

function closeModal() {
  state.showModal = false
  state.submitLoading = false
}

async function submitForm() {
  await formRef.value?.validate()
  state.submitLoading = true
  try {
    const payload = ${(target.needs_submit_normalize)?then("normalizeSubmitData(state.formModel)", "state.formModel")}
    if (state.dataId) {
      await ${api_export_name}.childUpdate({ ...payload, ${target.pk_name}: state.dataId })
      window.$message.success('更新成功')
    } else {
      await ${api_export_name}.childCreate(payload)
      window.$message.success('创建成功')
    }
    emit('saved')
    closeModal()
  } finally {
    state.submitLoading = false
  }
}

defineExpose({
  openModal,
})
</script>

<template>
  <NModal
    v-model:show="state.showModal"
    preset="card"
    draggable
    :mask-closable="false"
    :title="modalTitle"
    style="width: 720px"
    :segmented="{ content: true, action: true }"
  >
    <NSpin :show="state.loading">
      <NScrollbar class="max-h-[min(620px,calc(100vh-300px))] pr-16px">
        <NForm ref="formRef" :model="state.formModel" :rules="rules" label-placement="left" label-width="110" :disabled="state.loading || state.submitLoading">
<#list target.form_fields as field>
          <NFormItem label="${field.label}" path="${field.name}">
<#if field.form_widget == "number" || (field.data_type == "int" || field.data_type == "float")>
            <NInputNumber v-model:value="state.formModel.${field.name}" class="w-full" />
<#elseif field.form_widget == "textarea">
            <NInput v-model:value="state.formModel.${field.name}" type="textarea" :autosize="{ minRows: 3, maxRows: 8 }" />
<#elseif field.form_widget == "dict" && field.dict_code>
            <DictSelect v-model="state.formModel.${field.name}" dict-code="${field.dict_code}" />
<#elseif field.is_json>
            <NInput v-model:value="state.formModel.${field.name}" type="textarea" :autosize="{ minRows: 4, maxRows: 12 }" />
<#elseif field.data_type == "bool">
            <NSwitch v-model:value="state.formModel.${field.name}" />
<#elseif field.form_widget == "datetime" || field.data_type == "datetime">
            <NDatePicker v-model:formatted-value="state.formModel.${field.name}" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" class="w-full" clearable />
<#else>
            <NInput v-model:value="state.formModel.${field.name}" />
</#if>
          </NFormItem>
</#list>
        </NForm>
      </NScrollbar>
    </NSpin>

    <template #action>
      <NSpace justify="end">
        <NButton @click="closeModal">取消</NButton>
        <NButton type="primary" :loading="state.submitLoading" @click="submitForm">确认</NButton>
      </NSpace>
    </template>
  </NModal>
</template>
