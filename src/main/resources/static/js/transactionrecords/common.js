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

function amountReplace(prevValue) {
    let value = String(prevValue).replace(/\D/g, "");
    if (value.startsWith('0') && value.length > 1) {
        value = value.replace('0', '');
    }
    return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function loadData(grid, data) {
    grid.resetData(data);
}

function currentDate() {
    const date = titleDate();
    const currYear = date.getFullYear();
    const currMonth = date.getMonth() + 1 < 9 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1;
    const currDate = date.getDate() < 9 ? '0' + date.getDate() : date.getDate();
    return `${currYear}-${currMonth}-${currDate}`;
}

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