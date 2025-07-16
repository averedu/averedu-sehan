<template>
  <div class="bg-white dark:bg-[#252731] p-6 rounded-lg shadow-md">
    <div
      v-for="rowIdx in rowCount"
      :key="rowIdx"
      class="grid grid-cols-4 items-center gap-4 mb-2"
    >
      <template v-for="colIdx in 3" :key="colIdx">
        <div v-if="(rowIdx - 1) * 3 + colIdx <= colCount" class="search-item">
          <slot :name="`col${(rowIdx - 1) * 3 + colIdx}`" />
        </div>
        <!-- 조건이 부족한 경우 빈 셀 -->
        <div v-else />
      </template>
      <!-- 조회 버튼은 마지막 행에서만 표시 -->
      <div v-if="rowIdx === rowCount" class="flex justify-end items-center h-full">
        <button
          @click="onSearch"
          type="button"
          class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800"
        >
          조회
        </button>
      </div>
      <div v-else></div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  onSearch: Function,
  colCount: {
    type: Number,
    default: 3,
  },
})

const colsPerRow = 3
const rowCount = computed(() => Math.ceil(props.colCount / colsPerRow))
</script>
<style scoped></style>
