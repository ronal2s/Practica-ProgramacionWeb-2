module.exports = {
    convertJSONtoNormalDate: (date) => {
        const arr = date.split("-");
        const day = arr[2].split("T")[0]
        arr[2] = day;
        return `${arr[0]}-${arr[1]}-${arr[2]}`;
    }
}