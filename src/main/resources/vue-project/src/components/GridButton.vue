<template>
  <div v-if="featureAuth">
    <!-- 추가 버튼 -->
    <BaseButton v-if="canCreate" buttonType="primary" @click="addItem" :disabled="isAddRowInProgress">
      추가
    </BaseButton>
    <!-- 삭제 버튼 -->
    <BaseButton v-if="canDelete" buttonType="danger" @click="deleteItem">
      삭제
    </BaseButton>
    <!-- 저장 버튼 -->
    <BaseButton v-if="canUpdate" buttonType="primary" @click="saveData">
      저장
    </BaseButton>
    <!-- 엑셀 다운로드 버튼 -->
    <BaseButton v-if="canExcel" buttonType="warning" @click="downloadExcel">
      엑셀 다운로드
    </BaseButton>
  </div>
</template>

<script setup>
import { ref, computed, defineProps, defineEmits } from 'vue'
import BaseButton from './BaseButton.vue'

const emit = defineEmits(['update:rowData', 'add-row', 'save-data', 'delete-item', 'download-excel'])

const props = defineProps({
  featureAuth: {
    type: Object,
    required: true,
    // { add: true, delete: true, save: true, download: true }
  },
})

const isAddRowInProgress = ref(false)

const canCreate = computed(() => props.featureAuth.add)
const canUpdate = computed(() => props.featureAuth.save)
const canDelete = computed(() => props.featureAuth.delete)
const canExcel = computed(() => props.featureAuth.download)

const addItem = () => emit('add-item')
const deleteItem = () => emit('delete-item')
const saveData = () => emit('save-data')
const downloadExcel = () => emit('download-excel')
</script>

<style scoped>
/* 버튼 간격 스타일 */
BaseButton {
  margin-right: 10px;
}
button {
  margin-bottom: 10px;
}
</style>