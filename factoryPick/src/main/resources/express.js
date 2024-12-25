const express = require('express');
const mysql = require('mysql');
const cors = require('cors'); // CORS 문제 해결용

const app = express();
app.use(cors()); // CORS 활성화

// MySQL 연결 설정
const db = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password: 'password', // MySQL 비밀번호로 바꾸세요.
  database: 'factory',
});

// 데이터베이스 연결
db.connect((err) => {
  if (err) {
    console.error('MySQL 연결 오류:', err);
    return;
  }
  console.log('MySQL 연결 성공!');
});

// API: 특정 사용자 정보 가져오기
app.get('/api/user', (req, res) => {
  const userId = req.query.userId; // 요청에서 userId 받기

  const query = 'SELECT user_id, user_email FROM users WHERE user_id = ?';
  db.query(query, [userId], (err, result) => {
    if (err) {
      console.error('쿼리 실행 오류:', err);
      res.status(500).send('서버 오류');
    } else {
      res.json(result[0]); // 첫 번째 결과만 반환
    }
  });
});

// 서버 시작
const PORT = 8080;
app.listen(PORT, () => {
  console.log(`서버가 http://localhost:${PORT} 에서 실행 중입니다.`);
});