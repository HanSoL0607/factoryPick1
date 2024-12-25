const modal = document.getElementById("modal");
const startComparisonButton = document.getElementById("start-comparison");
const closeModalButton = document.getElementById("close-modal");

startComparisonButton.addEventListener("click", () => {
  modal.style.display = "flex";
});

closeModalButton.addEventListener("click", () => {
  modal.style.display = "none";
});

window.addEventListener("click", (event) => {
  if (event.target === modal) {
    modal.style.display = "none";
  }
});
// 1️⃣ 임시 로그인 함수: 사용자 ID를 로컬 스토리지에 저장
const fakeLogin = (userId) => {
  localStorage.setItem("loggedInUser", JSON.stringify({ userId }));
};

// 2️⃣ 로그인된 사용자 정보 가져오기
const getLoggedInUser = () => {
  const user = localStorage.getItem("loggedInUser");
  return user ? JSON.parse(user) : null;
};

const fetchUserInfo = async (email) => {
  try {
    const response = await fetch(`http://localhost:8080/api/user?email=${email}`);
    if (!response.ok) {
      throw new Error('사용자 정보를 불러오지 못했습니다.');
    }
    const userData = await response.json();
    return userData;
  } catch (error) {
    console.error('API 요청 오류:', error);
  }
};

const checkLoginStatus = async () => {
  const loggedInUser = getLoggedInUser();
  if (loggedInUser) {
    const userData = await fetchUserInfo(loggedInUser.userId);
    if (userData) {
      alert(`로그인된 상태입니다. 사용자 이메일: ${userData.userEmail}`);
    } else {
      alert('사용자 정보를 불러올 수 없습니다.');
    }
  } else {
    alert('로그인이 필요합니다. 로그인 후 이용해 주세요.');
  }
};

window.onload = () => {
  fakeLogin('user1@example.com');  // 이메일로 로그인 처리
  checkLoginStatus();  // 로그인 상태 확인
};