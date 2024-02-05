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

// 예제 데이터
const weeklyIncomeData = [
    {date: '1995-07-21', person_name: '홍길동', amount: 500, details: 1500, category:1},
    {date: '1995-07-21', person_name: '홍길동', amount: 400, details: 1400, category:2},
    // 필요에 따라 더 많은 데이터를 추가
];

// 그리드에 데이터 로드
loadData(weeklyIncomeData);

function appendRow() {
    grid.appendRow();
}