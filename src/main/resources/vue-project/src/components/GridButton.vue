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

// =======================[Props 정의]=======================
const props = defineProps({
  featureAuth: { type: String, required: true }, // CUDE 문자열 (권한)
})

// =======================[상태 및 권한 체크]=======================
const isAddRowInProgress = ref(false) // 행 추가 중 여부

// 각 버튼의 권한 여부 계산
const canCreate = computed(() => props.featureAuth.includes('C'))
const canUpdate = computed(() => props.featureAuth.includes('U'))
const canDelete = computed(() => props.featureAuth.includes('D'))
const canExcel = computed(() => props.featureAuth.includes('E'))

// =======================[행 추가 기능]=======================
const addItem = () => {
  emit('add-item')
}

// =======================[삭제 기능]=======================
const deleteItem = () => {
  emit('delete-item')
}

// =======================[저장 기능]=======================
const saveData = () => {
  emit('save-data')
}

// =======================[엑셀 다운로드 기능]=======================
const downloadExcel = () => {
  emit('download-excel')
}
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