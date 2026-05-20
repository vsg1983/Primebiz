const StateManager = {
    getCart() {
        return JSON.parse(localStorage.getItem('primebiz_cart') || '[]');
    },
    setCart(cart) {
        localStorage.setItem('primebiz_cart', JSON.stringify(cart));
    },
    addToCart(productId) {
        const cart = this.getCart();
        cart.push(productId);
        this.setCart(cart);
        return cart;
    },
    clearCart() {
        localStorage.removeItem('primebiz_cart');
    },
    getLang() {
        return I18nManager.currentLang;
    }
};

document.addEventListener('DOMContentLoaded', () => {
    I18nManager.init();
    
    const langSwitcher = document.getElementById('langSwitcher');
    if (langSwitcher) {
        langSwitcher.value = I18nManager.currentLang;
        langSwitcher.addEventListener('change', (e) => {
            I18nManager.setLanguage(e.target.value);
        });
    }

    const featuredGrid = document.getElementById('featuredProducts');
    if (featuredGrid) {
        loadFeaturedProducts();
    }
});

async function loadFeaturedProducts() {
    const grid = document.getElementById('featuredProducts');
    try {
        const products = await ApiClient.getProducts();
        const lang = I18nManager.currentLang;
        const addToCartText = I18nManager.t('product.addtocart');
        const priceText = I18nManager.t('product.price');

        grid.innerHTML = products.map(p => `
            <div class="product-card">
                <img src="https://via.placeholder.com/set-to-product-id-${p.id}" alt="${p.name}">
                <h3>${p.name}</h3>
                <p>${p.description}</p>
                <div class="price">${priceText}: ₹${p.price}</div>
                <button class="btn btn-primary" onclick="handleAddToCart(${p.id})">${addToCartText}</button>
            </div>
        `).join('');
    } catch (error) {
        grid.innerHTML = `<p style="color: red; text-align: center; grid-column: 1/-1;">${I18nManager.t('common.error')}</p>`;
    }
}

function handleAddToCart(productId) {
    StateManager.addToCart(productId);
    alert(I18nManager.t('common.success') + ' - Added to cart!');
}
