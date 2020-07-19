export const Encode = (input: string) => {
    const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=';
    let str = input;
    let output = '';

    for (let block = 0, charCode, i = 0, map = chars;
        str.charAt(i | 0) || (map = '=', i % 1);
        output += map.charAt((63 & block) >> 8 - i % 1 * 8)) { //Si deja de funcionar quitarle el () dentro de 63 & block

        charCode = str.charCodeAt(i += 3 / 4);

        if (charCode > 0xFF) {
            throw new Error("'encoded' failed: The string to be encoded contains characters outside of the Latin1 range.");
        }

        block = (block << 8) | charCode;
    }

    return output;
}

export const SetStorage = (key: string, data: string) => {
    window.localStorage.setItem(Encode(key), Encode(data));
}

export const GetStorage = (key: string) => {
    let data: string = ""
    let keyValue = window.localStorage.getItem(Encode(key));
    data = keyValue? keyValue: ""
    return atob(data);
}


export const Logout = () => {
    window.localStorage.clear();
    // window.location = "/auth"
}

export const Islogin = () => {
    let login = window.localStorage.getItem(Encode("login"));
    return login === null ? false : true;

}

export const isMobile = () => {
    let isMobile = false
    if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
        isMobile = true;
    }
    return isMobile;
}
