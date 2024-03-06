class AmountEditor {
    constructor(props) {
        const el = document.createElement('input');

        el.type = 'text';
        el.value = String(!!!props.value ? '0' : props.value);
        el.onkeyup = (event) => {
            el.value = amountReplace(el.value);
        }
        el.onpaste = () => {
            el.value = amountReplace(el.value);
        }

        this.el = el;
    }

    getElement() {
        return this.el;
    }

    getValue() {
        return this.el.value;
    }

    mounted() {
        this.el.select();
    }
}

function amountReplace(prevValue) {
    let value = String(prevValue).replace(/\D/g, "");
    if (value.startsWith('0') && value.length > 1) {
        value = value.replace('0', '');
    }
    return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}


const grid = new tui.Grid({
    el: document.getElementById('grid'),
    data: [], // 여기에 데이터를 채웁니다
    scrollX: false,
    scrollY: false,
    rowHeaders: ['rowNum'],
    columns: [
        {
            header: 'id',
            name: 'id',
            editor: 'text',
        },
        {
            header: '날짜',
            name: 'date',
            editor: 'datePicker',
            align: 'center',
            onAfterChange(ev) {
                updateDateValue(grid.getValue(ev.rowKey, 'id'), ev.value);
            },
        },
        {
            header: '성명(입금인)',
            name: 'person_name',
            editor: 'text',
            align: 'center',
            onAfterChange(ev) {
                updateNameValue(grid.getValue(ev.rowKey, 'id'), ev.value);
            },
        },
        {
            header: '금액',
            name: 'amount',
            align: 'right',
            editor: AmountEditor,
            onAfterChange(ev) {
                if (!!!ev.value) {
                    showToast('금액을 입력해 주세요.');
                    return;
                }
                const value = ev.value.replaceAll(',', '');
                if (!/^(0|[1-9][0-9]*)$/.test(value)) {
                    showToast('금액은 0으로 시작할 수 없습니다.');
                    return;
                }
                updateAmountValue(grid.getValue(ev.rowKey, 'id'), value);
                updateSummary();
            },
        },
        {
            header: '내용',
            name: 'details',
            align: 'left',
            editor: 'text',
            onAfterChange(ev) {
                updateDetailsValue(grid.getValue(ev.rowKey, 'id'), ev.value);
            },
        },
        {
            header: '구분',
            name: 'category',
            align: 'center',
            editor: 'text',
            onAfterChange(ev) {
                updateCategoryValue(grid.getValue(ev.rowKey, 'id'), ev.value);
            },
        }
    ],
    summary: {
        height: 40,
        position: 'bottom',
        columnContent: {
            amount: {
                useAutoSummary: false,
                template: function(valueMap) {
                    return `합계: 0 원`;
                }
            },
        }
    }
});

// 그리드에 데이터를 로드하는 함수
function loadData(data) {
    grid.resetData(data);
}

function findIncomeData(year, month) {
    grid.clear();
    const xhr = new XMLHttpRequest();
    xhr.open('GET', `/income/${year}/${month}`);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send();
    xhr.onload = function () {
        if (xhr.status === 200) {
            const data = JSON.parse(xhr.response);
            data.forEach(recode => recode.amount = amountReplace(recode.amount));
            loadData(data);
            return;
        }
        showToast('문제가 발생했습니다.');
    }
}

function appendRow() {
    const xhr = new XMLHttpRequest();
    xhr.open('POST', '/income');
    xhr.setRequestHeader('Content-Type', 'application/json');
    const date = titleDate();
    xhr.send(JSON.stringify({year: date.getFullYear(), month: date.getMonth() + 1, date: date.getDate()}));
    xhr.onload = function () {
        if (xhr.status === 200) {
            grid.appendRow({id: xhr.response, date: currentDate(), person_name: '', amount: 0, details: '', category: 1});
            updateSummary();
            return;
        }
        showToast('문제가 발생했습니다.');
    }
}

function removeRow() {
    if (!!!grid.getFocusedCell().rowKey) {
        showToast('삭제할 행을 선택해 주세요.');
        return;
    }
    if (!confirm('정말 삭제하시겠습니까?')) {
        return;
    }
    const xhr = new XMLHttpRequest();
    xhr.open('DELETE', `/income/${grid.getValue(grid.getFocusedCell().rowKey, 'id')}`);
    xhr.setRequestHeader('Content-Type', 'application/json');
    const date = titleDate();
    xhr.send();
    xhr.onload = function () {
        if (xhr.status === 204) {
            grid.removeRow(grid.getFocusedCell().rowKey);
            updateSummary();
            return;
        }
        showToast('문제가 발생했습니다.');
    }
}

function currentDate() {
    const date = titleDate();
    const currYear = date.getFullYear();
    const currMonth = date.getMonth() + 1 < 9 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1;
    const currDate = date.getDate() < 9 ? '0' + date.getDate() : date.getDate();
    return `${currYear}-${currMonth}-${currDate}`;
}

function yearMonthPickerEvent(year, month) {
    findIncomeData(year, month);
    changeTitle(year, month);
}

function moveToPreviousMonth() {
    const date = titleDate();
    date.setMonth(date.getMonth() - 1);
    findIncomeData(date.getFullYear(), date.getMonth() + 1);
    changeTitle(date.getFullYear(), date.getMonth() + 1);
}

function moveToNextMonth() {
    const date = titleDate()
    date.setMonth(date.getMonth() + 1);
    findIncomeData(date.getFullYear(), date.getMonth() + 1);
    changeTitle(date.getFullYear(), date.getMonth() + 1);
}

function changeTitle(year, month) {
    const monthNumber = document.getElementById('month-number');
    const yearTitle = document.getElementById('year-title');
    monthNumber.innerHTML = month;
    yearTitle.innerHTML = `${year}`;
}

function titleDate() {
    const monthNumber = document.getElementById('month-number');
    const yearTitle = document.getElementById('year-title');
    const date = new Date();
    const titleDate = new Date(Number(yearTitle.innerText), Number(monthNumber.innerText) - 1, 1);
    if (date.getFullYear() === titleDate.getFullYear() && date.getMonth() === titleDate.getMonth()) {
        return date;
    }
    return titleDate;
}

function updateCategoryValue(id, value) {
    const xhr = new XMLHttpRequest();
    xhr.open('PUT', `/income/category`, false);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify({id: id, category: value}));
    xhr.onload = function () {
        if (xhr.status !== 200) {
            showToast('저장에 실패했습니다.');
        }
    }
}

function updateNameValue(id, value) {
    const xhr = new XMLHttpRequest();
    xhr.open('PUT', `/income/name`, false);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify({id: id, name: value}));
    xhr.onload = function () {
        if (xhr.status !== 200) {
            showToast('저장에 실패했습니다.');
        }
    }
}

function updateDetailsValue(id, value) {
    const xhr = new XMLHttpRequest();
    xhr.open('PUT', `/income/details`, false);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify({id: id, details: value}));
    xhr.onload = function () {
        if (xhr.status !== 200) {
            showToast('저장에 실패했습니다.');
        }
    }
}

function updateDateValue(id, value) {
    const xhr = new XMLHttpRequest();
    xhr.open('PUT', `/income/date`, false);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify({id: id, date: value}));
    xhr.onload = function () {
        if (xhr.status !== 200) {
            showToast('저장에 실패했습니다.');
        }
    }
}

function updateAmountValue(id, value) {
    const xhr = new XMLHttpRequest();
    xhr.open('PUT', `/income/amount`, false);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify({id: id, amount: value}));
    xhr.onload = function () {
        if (xhr.status !== 200) {
            showToast('저장에 실패했습니다.');
        }
    }
}

function updateSummary() {
    grid.setSummaryColumnContent('amount', {
        template(summary) {
            let amounts = grid.getColumnValues('amount');
            let sum = amounts
                .map(it => Number(String(it).replaceAll(',', '')))
                .reduce((a, b) => a + b, 0);
            return `합계: ${amountReplace(sum)} 원`
        }
    });
}

const date = new Date();
grid.hideColumn('id');
findIncomeData(date.getFullYear(), date.getMonth() + 1);
changeTitle(date.getFullYear(), date.getMonth() + 1);
const yearMonthModal = new createYearMonthModal(yearMonthPickerEvent, document.getElementById('year-title'));
updateSummary();


