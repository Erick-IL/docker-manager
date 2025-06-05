window.addEventListener("DOMContentLoaded", () => {
    const toast = document.getElementById("error-toast");
    if (toast) {
        void toast.offsetWidth;
        toast.classList.add("show");
        const timeoutId = setTimeout(() => {
            closeToast();
        }, 5000);
        const closeBtn = toast.querySelector(".close-toast");
        closeBtn.addEventListener("click", () => {
            clearTimeout(timeoutId);
            closeToast();
        });
        function closeToast() {
            toast.classList.remove("show");
            setTimeout(() => toast.remove(), 500);
        }
    }
});
