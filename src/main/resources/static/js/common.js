function showToast(message) {
    var toast = document.createElement("div");
    toast.textContent = message;
    toast.className = "toast";
    document.body.appendChild(toast);

    // 게이지 바 컨테이너 생성 및 추가
    var progressContainer = document.createElement("div");
    progressContainer.className = "toast-progress-container";
    toast.appendChild(progressContainer);

    // 게이지 바 생성 및 추가
    var progressBar = document.createElement("div");
    progressBar.className = "toast-progress";
    progressContainer.appendChild(progressBar);

    // 토스트 메시지 보여주기
    setTimeout(function() {
        toast.classList.add("show");
        progressBar.style.width = "100%"; // 게이지 바 시작
    }, 100);

    // 일정 시간 후 토스트 메시지 제거
    setTimeout(function() {
        toast.classList.remove("show");
        setTimeout(function() {
            document.body.removeChild(toast);
        }, 500); // 투명해지는 동안 기다림
    }, 3000); // 3초 동안 표시
}

function createYearMonthModal(yearMonthCallBack, yearElement) {
    const modalContainer = document.createElement('div');
    modalContainer.classList.add('modal-container');

    const modalContent = document.createElement('div');
    modalContent.classList.add('modal-content');
    modalContainer.appendChild(modalContent);

    const closeButton = document.createElement('span');
    closeButton.textContent = 'X';
    closeButton.classList.add('close-button');
    closeButton.onclick = () => modalContainer.style.display = 'none';
    modalContent.appendChild(closeButton);

    const yearSelect = document.createElement('select');
    yearSelect.classList.add('year-select');
    const currentYear = new Date().getFullYear();
    for (let year = 2000; year <= currentYear + 10; year++) {
        const option = new Option(year, year);
        yearSelect.appendChild(option);
    }
    modalContent.appendChild(yearSelect);
    yearSelect.value = yearElement.innerText;

    for (let month = 1; month <= 12; month++) {
        const monthButton = document.createElement('button');
        monthButton.classList.add('month-button');
        monthButton.textContent = `${month}월`;
        monthButton.onclick = () => {
            modalContainer.style.display = 'none';
            yearMonthCallBack(yearSelect.value, month);
        };
        modalContent.appendChild(monthButton);
    }

    document.body.appendChild(modalContainer);

    // 모달을 여는 함수
    this.openModal = () => {
        modalContainer.style.display = 'flex';
        modalContainer.style.justifyContent = 'center';
        modalContainer.style.alignItems = 'center';
    };
}
