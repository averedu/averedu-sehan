<template>
  <div class="flex h-screen bg-gray-200 font-roboto">
    <LeftMenu />
    <div class="flex-1 flex flex-col overflow-hidden">
      <Header />
      <main class="flex flex-col flex-1 min-h-0 bg-gray-200">
        <!-- Main Content -->
        <router-view />
      </main>
    </div>
  </div>
</template>

<script setup>
import LeftMenu from '@/components/LeftMenu.vue'
import Header from '@/components/Header.vue'
</script>

<style lang="scss" scoped></style>
