class AmountEditor {
    constructor(props) {
        const el = document.createElement('input');

        el.type = 'text';
        el.value = String(!!!props.value ? '0' : props.value);

        this.el = el;
    }

    getElement() {
        return this.el;
    }

    getValue() {
        return this.el.value
    }

    mounted() {
        this.el.select();
    }
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
        },
        {
            header: '성명(입금인)',
            name: 'person_name',
            editor: 'text',
        },
        {
            header: '금액',
            name: 'amount',
            editor: AmountEditor,
            validation: {
                regExp: /^[1-9][0-9]*$/
            },
            onBeforeChange(ev) {
                console.log(ev);
                console.log('Before change:' + ev.value);
            },
            onAfterChange(ev) {
                console.log(ev);
                console.log('After change:' + ev.value);
            },
        },
        {
            header: '내용',
            name: 'details',
            editor: 'text'
        },
        {
            header: '구분',
            name: 'category',
            editor: 'text'
        }
    ],
    summary: {
        height: 40,
        position: 'bottom',
        columnContent: {
            amount: {
                template: function(valueMap) {
                    return `합계: ${valueMap.sum} 원`;
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
            loadData(JSON.parse(xhr.response));
            return;
        }
        alert('문제가 발생했습니다.');
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
            return;
        }
        alert('문제가 발생했습니다.');
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

const date = new Date();
findIncomeData(date.getFullYear(), date.getMonth() + 1);
changeTitle(date.getFullYear(), date.getMonth() + 1);
const yearMonthModal = new createYearMonthModal(yearMonthPickerEvent, document.getElementById('year-title'));
grid.hideColumn('id');
