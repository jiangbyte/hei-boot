<!-- Author: Charlie -->
<!--
  深色侧边栏专用主题 Provider：把包裹的 n-menu / n-scrollbar 固定渲染为深底浅字，
  不随全局 light/dark 模式切换（深色侧边栏 ≠ 暗黑模式）。
  配色引用 style.css 中的模式感知变量（--sidebar-*）：
    · 浅色模式：深蓝灰 #1f2733 + 白内容，高对比；
    · 暗黑模式：更深一档 #151a24 + 暗内容，与内容协调但保持面板区分度。
  背景由 layouts/index.vue 的 .n-pro-layout__aside 提供，菜单容器透明。
-->
<script setup lang="ts">
import type { GlobalThemeOverrides } from 'naive-ui'
import { darkTheme } from 'naive-ui'

const sidebarMenuThemeOverrides: GlobalThemeOverrides = {
  Menu: {
    // 容器透明，背景由 aside 提供
    color: 'transparent',
    // 菜单项
    itemColor: 'transparent',
    itemColorHover: 'var(--sidebar-item-hover-bg)',
    itemColorActive: 'var(--sidebar-item-active-bg)',
    itemColorActiveHover: 'var(--sidebar-item-active-hover-bg)',
    // 文字
    itemTextColor: 'var(--sidebar-text)',
    itemTextColorHover: 'var(--sidebar-text-hover)',
    itemTextColorActive: 'var(--sidebar-text-active)',
    itemTextColorActiveHover: 'var(--sidebar-text-active)',
    itemTextColorChildActive: 'var(--sidebar-text-active)',
    itemTextColorChildActiveHover: 'var(--sidebar-text-active)',
    // 图标
    itemIconColor: 'var(--sidebar-text)',
    itemIconColorHover: 'var(--sidebar-text-hover)',
    itemIconColorActive: 'var(--sidebar-text-active)',
    itemIconColorActiveHover: 'var(--sidebar-text-active)',
    itemIconColorChildActive: 'var(--sidebar-text-active)',
    itemIconColorChildActiveHover: 'var(--sidebar-text-active)',
    // 展开箭头
    arrowColor: 'var(--sidebar-text)',
    arrowColorActive: 'var(--sidebar-text-active)',
    // 分组标题
    groupTextColor: 'var(--sidebar-group-text)',
    borderRadius: '6px',
    // 折叠态弹出子菜单
    popupColor: 'var(--sidebar-bg)',
    popupBorderRadius: '8px',
  },
}
</script>

<template>
  <n-config-provider
    :theme="darkTheme"
    :theme-overrides="sidebarMenuThemeOverrides"
  >
    <slot />
  </n-config-provider>
</template>
