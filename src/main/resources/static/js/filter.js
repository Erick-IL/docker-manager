window.addEventListener("DOMContentLoaded", () => {
    const searchInput = document.getElementById("search-input");
    const statusFilter = document.getElementById("status-filter");
    filterContainers(searchInput.value.toLowerCase(), statusFilter.value);

    searchInput.addEventListener("input", () => {
        const query = searchInput.value.toLowerCase();
        const status = statusFilter.value;
        filterContainers(query, status);
    });

    statusFilter.addEventListener("change", () => {
        const query = searchInput.value.toLowerCase();
        const status = statusFilter.value;
        filterContainers(query, status);
    });


    function filterContainers(query, status) {

        let containers = document.querySelectorAll(".container-card");
        containers.forEach(container => {
            const nameSpan = container.querySelector('.container-name span');
            const nameText = nameSpan.textContent.toLowerCase();

            const stateSpan = container.querySelector('.status-text');
            const stateText = stateSpan.textContent;

            const tagSpan = container.querySelector('.image-span');
            const tagText = tagSpan.textContent.toLowerCase();

            const matchesQuery = nameText.includes(query.toLowerCase());
            const matchesTag = tagText.match(query.toLowerCase());
            const matchesStatus = (status === 'Todos') || (stateText === status);

            container.style.display = (matchesQuery || matchesTag) && matchesStatus ? '' : 'none';
        });
    }
});
