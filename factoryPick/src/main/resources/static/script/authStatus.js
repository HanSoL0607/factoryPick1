// window.onload 이벤트로 전체 로직 실행
window.onload = function () {
    // 로그인 상태 확인 요청
    fetch('/api/auth-status', { method: 'GET', cache: 'no-cache' })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('전체 응답:', data);  // 전체 객체 확인

            // 인증 상태 확인: 명확하게 조건문 작성
            const isAuthenticated = data.authenticated === true;
            console.log('Authenticated 상태:', isAuthenticated);  // true 또는 false 확인

            const authButton = document.getElementById('authButton');
            if (authButton) {
                setupAuthButton(authButton, isAuthenticated);
            } else {
                console.error('authButton 요소를 찾을 수 없습니다.');
            }
        })
        .catch(error => console.error('Error fetching auth status:', error));

    // 페이지 이동 버튼 이벤트 설정
    setupPageNavigation('.myPage', '/mypage/mypage_recommend');
    setupPageNavigation('.property', '/property');
    setupPageNavigation('.intro', '/intro');
    setupPageNavigation('.home', '/');
    setupPageNavigation('.login', '/login');
    setupPageNavigation('.agent', '/agent');
};

// 인증 버튼 설정 함수
function setupAuthButton(button, isAuthenticated) {
    if (isAuthenticated) {
        button.textContent = '로그아웃';
        button.onclick = async () => {
            try {
                const response = await fetch('/logout', { method: 'POST', credentials: 'same-origin' });
                if (response.ok) {
                    clearCookiesAndRedirect();  // 쿠키 삭제 후 리다이렉트
                } else {
                    console.error('로그아웃 실패:', response.status);
                }
            } catch (error) {
                console.error('로그아웃 중 오류 발생:', error);
            }
        };
    } else {
        button.textContent = '로그인 / 회원가입';
        button.onclick = () => window.location.href = '/login';
    }
}

// 페이지 이동 버튼 설정 함수
function setupPageNavigation(selector, path) {
    const button = document.querySelector(selector);
    if (button) {
        button.addEventListener('click', () => {
            window.location.href = path;
        });
    } else {
        console.error(`${selector} 요소를 찾을 수 없습니다.`);
    }
}

// 쿠키 삭제 후 리다이렉트 처리 함수
function clearCookiesAndRedirect() {
    document.cookie = 'JSESSIONID=; Path=/; Expires=Thu, 01 Jan 1970 00:00:00 GMT; Secure; HttpOnly';
    console.log('JSESSIONID 쿠키가 삭제되었습니다.');

    // 로그아웃 후 로그인 페이지로 이동
    window.location.href = '/login?logout=true';
}
