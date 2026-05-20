const API_BASE_URL = 'http://localhost:8080'; // Spring Cloud Gateway

async function apiRequest(endpoint, options = {}) {
    const lang = StateManager.getLang();
    const headers = {
        'Content-Type': 'application/json',
        'Accept-Language': lang === 'ta' ? 'ta' : 'en',
        ...options.headers
    };

    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, { ...options, headers });
        if (!response.ok) throw new Error(`API Error: ${response.statusText}`);
        return await response.json();
    } catch (error) {
        console.error('API Request Failed:', error);
        throw error;
    }
}

const ApiClient = {
    getProducts: () => apiRequest('/product-service/products'),
    getAdminStats: () => apiRequest('/admin-service/admin/stats'),

    getProductById: (id) => apiRequest(`/product-service/products/${id}`),
    createOrder: (orderData) => apiRequest('/order-service/orders', {
        method: 'POST',
        body: JSON.stringify(orderData)
    }),
    getUserProfile: (userId) => apiRequest(`/user-service/users/${userId}`),
};
