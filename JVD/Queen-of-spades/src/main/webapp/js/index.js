function hide(formName) {
    if (formName === "authorization") {
        document.getElementById("authorization").hidden = true;
        document.getElementById("registration").hidden = false;
    }
    if (formName === "registration") {
        document.getElementById("registration").hidden = true;
        document.getElementById("authorization").hidden = false;
    }
}