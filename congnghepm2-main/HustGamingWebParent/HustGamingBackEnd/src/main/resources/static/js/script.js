document.addEventListener('DOMContentLoaded', function() {
    fetch('/check-login-status')  // Gọi API kiểm tra đăng nhập
        .then(response => response.json())
        .then(data => {
            const isLoggedIn = data.isLoggedIn;  // API trả về một giá trị xác thực

            const authLinks = document.querySelector('.auth-links');
            const userInfo = document.querySelector('.user-info');

            if (isLoggedIn) {
                authLinks.style.display = 'none';
                userInfo.style.display = 'flex';
            } else {
                authLinks.style.display = 'flex';
                userInfo.style.display = 'none';
            }
        })
        .catch(error => {
            console.error('Lỗi khi kiểm tra trạng thái đăng nhập:', error);
        });
});
