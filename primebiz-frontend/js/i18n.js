const translations = {
    en: {
        'nav.home': 'Home',
        'nav.products': 'Products',
        'nav.cart': 'Cart',
        'hero.title': 'Quality Products, Delivered to Your Door',
        'hero.subtitle': 'Bringing the best of Madurai and beyond to your home.',
        'hero.cta': 'Shop Now',
        'featured.title': 'Featured Products',
        'product.addtocart': 'Add to Cart',
        'product.price': 'Price',
        'product.name': 'Product Name',
        'product.desc': 'Description',
        'cart.title': 'Your Shopping Cart',
        'cart.total': 'Total Amount',
        'cart.checkout': 'Proceed to Checkout',
        'cart.empty': 'Your cart is currently empty.',
        'checkout.title': 'Checkout',
        'checkout.name': 'Full Name',
        'checkout.address': 'Shipping Address',
        'checkout.phone': 'Phone Number',
        'checkout.payment': 'Payment Method',
        'checkout.placeorder': 'Place Order',
        'common.success': 'Success!',
        'common.error': 'Something went wrong. Please try again.'
    },
    ta: {
        'nav.home': 'முகப்பு',
        'nav.products': 'பொருட்கள்',
        'nav.cart': 'கூடை',
        'hero.title': 'தரமான பொருட்கள், உங்கள் வீட்டு வாசலில்',
        'hero.subtitle': 'மதுரை மற்றும் அதைdelà இருந்த சிறந்தவற்றை உங்கள் இல்லத்திற்கு கொண்டு வருகிறோம்.',
        'hero.cta': 'இப்பொழுது வாங்குங்கள்',
        'featured.title': 'சிறப்பு பொருட்கள்',
        'product.addtocart': 'கூடையில் சேர்க்கவும்',
        'product.price': 'விலை',
        'product.name': 'பொருள் பெயர்',
        'product.desc': 'விளக்கம்',
        'cart.title': 'உங்கள் ஷாப்பிங் கூடை',
        'cart.total': 'மொத்த தொகை',
        'cart.checkout': 'செலுத்துதலுக்குச் செல்லவும்',
        'cart.empty': 'உங்கள் கூடை தற்போது காலியாக உள்ளது.',
        'checkout.title': 'செலுத்துதல்',
        'checkout.name': 'முழு பெயர்',
        'checkout.address': 'டெலிவரி முகவரி',
        'checkout.phone': 'தொலைபேசி எண்',
        'checkout.payment': 'கட்டண முறை',
        'checkout.placeorder': 'ஆர்டரை உறுதி செய்யவும்',
        'common.success': 'வெற்றி!',
        'common.error': 'ஏதோ தவறு நடந்துவிட்டது. மீண்டும் முயற்சிக்கவும்.'
    }
};

const I18nManager = {
    currentLang: 'en',

    init() {
        this.currentLang = localStorage.getItem('preferredLang') || 'en';
        this.applyTranslations();
    },

    setLanguage(lang) {
        this.currentLang = lang;
        localStorage.setItem('preferredLang', lang);
        this.applyTranslations();
    },

    applyTranslations() {
        document.querySelectorAll('[data-i18n]').forEach(el => {
            const key = el.getAttribute('data-i18n');
            if (translations[this.currentLang][key]) {
                el.textContent = translations[this.currentLang][key];
            }
        });
    },

    t(key) {
        return translations[this.currentLang][key] || key;
    }
};
