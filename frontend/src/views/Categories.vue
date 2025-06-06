<template>
  <div class="categories">
    <h1>Categories</h1>
    <div class="categories-controls">
      <button @click="showNewCategoryForm = true" class="btn-primary">Add Category</button>
    </div>

    <div v-if="showNewCategoryForm" class="category-form">
      <h3>New Category</h3>
      <form @submit.prevent="handleSubmit">
        <div class="form-group">
          <label for="name">Name</label>
          <input 
            id="name"
            v-model="newCategory.name"
            type="text"
            required
          >
        </div>
        
        <div class="form-group">
          <label for="color">Color</label>
          <input 
            id="color"
            v-model="newCategory.color"
            type="color"
            required
          >
        </div>

        <div class="form-actions">
          <button type="submit" class="btn-primary">Save</button>
          <button type="button" @click="showNewCategoryForm = false" class="btn-secondary">Cancel</button>
        </div>
      </form>
    </div>

    <div class="categories-grid">
      <div v-for="category in categories" :key="category.id" 
           class="category-card" :style="{ borderColor: category.color }">
        <div class="category-name">{{ category.name }}</div>
        <div class="category-color" :style="{ backgroundColor: category.color }"></div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { api } from '@/api/api';

export default {
  name: 'Categories',
  setup() {
    const categories = ref([]);
    const showNewCategoryForm = ref(false);
    const newCategory = ref({
      name: '',
      color: '#1976d2'
    });

    const fetchCategories = async () => {
      try {
        const data = await api.getCategories();
        categories.value = data;
      } catch (error) {
        console.error('Error fetching categories:', error);
      }
    };

    const handleSubmit = async () => {
      try {
        await api.createCategory(newCategory.value);
        showNewCategoryForm.value = false;
        newCategory.value = {
          name: '',
          color: '#1976d2'
        };
        await fetchCategories();
      } catch (error) {
        console.error('Error creating category:', error);
      }
    };

    onMounted(fetchCategories);

    return {
      categories,
      showNewCategoryForm,
      newCategory,
      handleSubmit
    };
  }
};
</script>

<style scoped>
.categories {
  padding: 20px;
}

.categories-controls {
  margin-bottom: 20px;
}

.category-form {
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: 500;
}

.form-group input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.form-group input[type="color"] {
  height: 40px;
  padding: 2px;
}

.form-actions {
  display: flex;
  gap: 10px;
  margin-top: 20px;
}

.btn-primary,
.btn-secondary {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-primary {
  background: #1976d2;
  color: white;
}

.btn-secondary {
  background: #e0e0e0;
  color: #333;
}

.categories-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 20px;
}

.category-card {
  background: white;
  padding: 15px;
  border-radius: 6px;
  box-shadow: 0 1px 3px rgba(0,0,0,0.1);
  border-left: 4px solid;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.category-name {
  font-weight: 500;
}

.category-color {
  width: 24px;
  height: 24px;
  border-radius: 50%;
}
</style>
