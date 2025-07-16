<template>
  <div class="flex">
    <div
      :class="isOpen ? 'block' : 'hidden'"
      class="fixed inset-0 z-20 transition-opacity bg-black opacity-50 lg:hidden"
      @click="isOpen = false"
    />

    <!-- Sidebar -->
    <div
      :class="isOpen ? 'translate-x-0 ease-out' : '-translate-x-full ease-in'"
      class="fixed inset-y-0 left-0 z-30 w-64 overflow-y-auto transition duration-300 transform bg-gray-900 lg:translate-x-0 lg:static lg:inset-0"
    >
      <div class="flex items-flex-start justify-flex-start mt-8">
        <div class="flex items-flex-start">
          <span class="mx-2 text-2xl font-semibold text-white">averedu</span>
        </div>
      </div>
    <!-- 기존 동적 메뉴 주석처리 -->
    <!--
    <nav v-for="(menuList, idx) in sysMenuList" :key="idx" class="mt-10">
      <RouterLink
        :to="menuList.pgmPathNm"
        class="flex items-center px-6 py-2 mt-4 duration-200 border-l-4"
        :class="[$route.path === menuList.pgmPathNm ? activeClass : inactiveClass]"
      >
        <span class="mx-4">{{ menuList.menuNm }}</span>
      </RouterLink>
    </nav>
    -->

    <!-- 하드코딩 메뉴 -->
    <nav class="mt-10">
      <RouterLink
        to="/main/userMng"
        class="flex items-center px-6 py-2 mt-4 duration-200 border-l-4"
        :class="[$route.path === '/main/userMng' ? activeClass : inactiveClass]"
      >
        <span class="mx-4">사용자관리</span>
      </RouterLink>
      <RouterLink
        to="/main/deptCdMgmt"
        class="flex items-center px-6 py-2 mt-4 duration-200 border-l-4"
        :class="[$route.path === '/main/deptCdMgmt' ? activeClass : inactiveClass]"
      >
        <span class="mx-4">부서코드관리</span>
      </RouterLink>
    </nav>
    </div>
  </div>
</template>

<script setup>
import { RouterLink, RouterView } from 'vue-router'
import axios from 'axios'
import { ref } from 'vue'
import { useSidebar } from '../composables/useSidebar'

const { isOpen } = useSidebar()
const activeClass = ref('bg-gray-600 bg-opacity-25 text-gray-100 border-gray-100')
const inactiveClass = ref(
  'border-gray-900 text-gray-500 hover:bg-gray-600 hover:bg-opacity-25 hover:text-gray-100',
)

const sysMenuList = ref([])

axios
  .get('/restApi/prj/sys/sysMenuList.do')
  .then((restApi) => {
    console.log('sysMenuList : ', restApi.data)
    if (restApi.status === 200) {
      sysMenuList.value = restApi.data
    }
  })
  .catch((error) => {
    console.log('sysMenuList : ', error)
  })
</script>

<style lang="scss" scoped></style>
