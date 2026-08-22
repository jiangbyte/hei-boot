<!--
  由 HEI 代码生成器生成。
  Author: ${plan.author}
  生成时间：${generated_at}
-->

<script setup lang="ts">
import { ${api_export_name} } from '@/api'
import { <#if target.has_detail_dict>createTagColor, dictTypeColor, dictTypeData, </#if>displayValue, formatDateTime } from '@/utils'
import { reactive } from 'vue'

const state = reactive({
  showModal: false,
  loading: false,
  detail: {} as any,
})

async function openModal(id: string) {
  state.detail = {}
  state.showModal = true
  await fetchDetail(id)
}

async function fetchDetail(id: string) {
  state.loading = true
  try {
    const response = await ${api_export_name}.childDetail({ id })
    state.detail = response.data ?? {}
  } finally {
    state.loading = false
  }
}
<#if target.has_detail_json>

function formatJsonValue(value: unknown) {
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

defineExpose({
  openModal,
})
</script>

<template>
  <NModal v-model:show="state.showModal" preset="card" draggable :mask-closable="false" title="${(plan.sub_business_name)?has_content?then(plan.sub_business_name, "明细")}详情" style="width: 680px">
    <NScrollbar class="max-h-[min(620px,calc(100vh-300px))] pr-16px">
      <NSpin :show="state.loading">
        <NDescriptions label-placement="left" bordered :column="1">
<#list target.detail_fields as field>
          <NDescriptionsItem label="${field.label}">
<#if field.dict_code?? && field.dict_code?has_content>
            <NTag :color="createTagColor(dictTypeColor('${field.dict_code}', state.detail.${field.name}))" :bordered="false">
              ${"{{"} dictTypeData('${field.dict_code}', state.detail.${field.name}) || displayValue(state.detail.${field.name}) ${"}" }}
            </NTag>
<#elseif field.data_type == "datetime">
            ${"{{"} formatDateTime(state.detail.${field.name}) ${"}" }}
<#elseif field.is_json>
            <NCode :code="formatJsonValue(state.detail.${field.name})" language="json" word-wrap />
<#else>
            ${"{{"} displayValue(state.detail.${field.name}) ${"}" }}
</#if>
          </NDescriptionsItem>
</#list>
          <NDescriptionsItem label="创建时间">${"{{"} formatDateTime(state.detail.created_at) ${"}" }}</NDescriptionsItem>
          <NDescriptionsItem label="创建人">${"{{"} displayValue(state.detail.created_name || state.detail.created_by) ${"}" }}</NDescriptionsItem>
          <NDescriptionsItem label="更新时间">${"{{"} formatDateTime(state.detail.updated_at) ${"}" }}</NDescriptionsItem>
          <NDescriptionsItem label="更新人">${"{{"} displayValue(state.detail.updated_name || state.detail.updated_by) ${"}" }}</NDescriptionsItem>
        </NDescriptions>
      </NSpin>
    </NScrollbar>
  </NModal>
</template>
