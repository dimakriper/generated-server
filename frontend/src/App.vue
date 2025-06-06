<template>
  <div class="app">
    <nav class="navigation">
      <router-link to="/" class="nav-link">Dashboard</router-link>
      <router-link to="/transactions" class="nav-link">Transactions</router-link>
      <router-link to="/categories" class="nav-link">Categories</router-link>
    </nav>
    <main>
      <router-view></router-view>
    </main>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
.app {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.navigation {
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
}

.nav-link {
  text-decoration: none;
  color: #333;
  padding: 8px 16px;
  border-radius: 4px;
}

.nav-link.router-link-active {
  background-color: #e0e0e0;
}
</style>
