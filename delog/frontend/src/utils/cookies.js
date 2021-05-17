function saveAuthToCookie(value) {
  document.cookie = `auth=${value}`;
}

function saveNicknameToCookie(value) {
  document.cookie = `nickname=${value}`;
}


function saveUidToCookie(value) {
  document.cookie = `uid=${value}`;
  // document.cookie = "safeCookie1=foo; SameSite=Strict"; 
  // document.cookie = "safeCookie1=foo; SameSite=Lax"; 
  // document.cookie = "safeCookie2=foo"; // chrome 80 over : default Lax 
  // document.cookie = "crossCookie=bar; SameSite=None; Secure"; 
  // document.cookie = "safeCookie1=foo; SameSite=None";

}


function getAuthFromCookie() {
  return document.cookie.replace(
    /(?:(?:^|.*;\s*)auth\s*=\s*([^;]*).*$)|^.*$/,
    '$1',
  );
}

function getNicknameFromCookie() {
  return document.cookie.replace(
    /(?:(?:^|.*;\s*)nickname\s*=\s*([^;]*).*$)|^.*$/,
    '$1',
  );
}

function getUidFromCookie() {
  return document.cookie.replace(
    /(?:(?:^|.*;\s*)uid\s*=\s*([^;]*).*$)|^.*$/,
    '$1',
  );
}

function deleteCookie(value) {
  document.cookie = `${value}=; expires=Thu, 01 Jan 1970 00:00:01 GMT;`;
}

export {
  saveAuthToCookie,
  saveNicknameToCookie,
  saveUidToCookie,
  getAuthFromCookie,
  getNicknameFromCookie,
  getUidFromCookie,
  deleteCookie,
};
