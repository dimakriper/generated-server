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
      <CategoryItem
        v-for="category in categories"
        :key="category.id"
        :category="category"
        @edit="editCategory"
        @delete="deleteCategory"
      />
    </div>

    <div v-if="showEditForm" class="category-form">
      <h3>Edit Category</h3>
      <form @submit.prevent="handleUpdate">
        <div class="form-group">
          <label for="edit-name">Name</label>
          <input 
            id="edit-name"
            v-model="editingCategory.name"
            type="text"
            required
          >
        </div>
        
        <div class="form-group">
          <label for="edit-color">Color</label>
          <input 
            id="edit-color"
            v-model="editingCategory.color"
            type="color"
            required
          >
        </div>

        <div class="form-actions">
          <button type="submit" class="btn-primary">Update</button>
          <button type="button" @click="showEditForm = false" class="btn-secondary">Cancel</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { api } from '@/api/api';
import CategoryItem from '@/components/CategoryItem.vue';

export default {
  name: 'Categories',
  components: {
    CategoryItem
  },
  setup() {
    const categories = ref([]);
    const showNewCategoryForm = ref(false);
    const newCategory = ref({
      name: '',
      color: '#1976d2'
    });
    const showEditForm = ref(false);
    const editingCategory = ref(null);

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

    const editCategory = (category) => {
      editingCategory.value = { ...category };
      showEditForm.value = true;
    };

    const handleUpdate = async () => {
      try {
        await api.updateCategory(editingCategory.value.id, editingCategory.value);
        showEditForm.value = false;
        editingCategory.value = null;
        await fetchCategories();
      } catch (error) {
        console.error('Error updating category:', error);
      }
    };

    const deleteCategory = async (id) => {
      if (!confirm('Are you sure you want to delete this category?')) return;
      
      try {
        await api.deleteCategory(id);
        await fetchCategories();
      } catch (error) {
        console.error('Error deleting category:', error);
      }
    };

    onMounted(fetchCategories);

    return {
      categories,
      showNewCategoryForm,
      newCategory,
      showEditForm,
      editingCategory,
      handleSubmit,
      editCategory,
      handleUpdate,
      deleteCategory
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
</style>
