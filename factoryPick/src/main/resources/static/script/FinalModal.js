document.addEventListener('DOMContentLoaded', () => {
    const modal = document.getElementById("modal");
    const startComparisonButton = document.getElementById("start-comparison");
    const closeModalButton = document.getElementById("close-modal");

    startComparisonButton.addEventListener("click", () => {
        const firstFactory = document.getElementById("firstFactory").value;
        const secondFactory = document.getElementById("secondFactory").value;

        console.log("First Factory:", firstFactory, "Second Factory:", secondFactory);

        // fetch 호출: 서버로 데이터 요청
        fetch(`http://localhost:8080/api/factory/info?first=${firstFactory}&second=${secondFactory}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then(data => {
                console.log("Received Data:", data);

                // 데이터 바인딩
				document.getElementById('firstComplexName').innerText = data.first.name;
				document.getElementById('secondComplexName').innerText = data.second.name;
				document.getElementById('firstSize').innerText = data.first.size;
                document.getElementById('secondSize').innerText = data.second.size;
                document.getElementById('firstCompletionDate').innerText = data.first.completionDate;
                document.getElementById('secondCompletionDate').innerText = data.second.completionDate;
                document.getElementById('firstHighway').innerText = data.first.highway;
                document.getElementById('secondHighway').innerText = data.second.highway;
                document.getElementById('firstSeaport').innerText = data.first.seaport;
                document.getElementById('secondSeaport').innerText = data.second.seaport;
                document.getElementById('firstStation').innerText = data.first.station;
                document.getElementById('secondStation').innerText = data.second.station;
                document.getElementById('firstAirport').innerText = data.first.airport;
                document.getElementById('secondAirport').innerText = data.second.airport;

                // 모달 열기
                console.log("Opening modal...");
                modal.style.display = "flex";
            })
            .catch(error => {
                console.error('데이터 요청 오류:', error);
                alert("데이터를 불러오는 데 실패했습니다.");
            });
    });

    closeModalButton.addEventListener("click", () => {
        modal.style.display = "none";
    });

    window.addEventListener("click", (event) => {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    });
});
