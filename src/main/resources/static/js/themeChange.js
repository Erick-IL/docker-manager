const themeIcon = document.getElementById("theme-icon");

function toggleTheme() {
    const isDark = document.body.classList.toggle("dark");
    localStorage.setItem("theme", isDark ? "dark" : "light");

    // Alterna o ícone
    themeIcon.className = isDark ? "fas fa-sun" : "fas fa-moon";

    // Adiciona classe de rotação
    themeIcon.classList.add("rotate");

    // Remove após a animação
    setTimeout(() => {
        themeIcon.classList.remove("rotate");
    }, 400);
}

// Aplica o tema salvo ao carregar
window.addEventListener("DOMContentLoaded", () => {
    const isDark = localStorage.getItem("theme") === "dark";
    if (isDark) {
        document.body.classList.add("dark");
        themeIcon.className = "fas fa-sun";
    }
});