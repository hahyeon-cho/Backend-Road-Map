document.addEventListener('DOMContentLoaded', function () {
    var levelText = document.querySelector('.problem-info').textContent;
    updateLevelDisplay(levelText);
});

function updateLevelDisplay(levelText) {
    var className = '';

    switch (levelText) {
        case 'Easy':
            className = 'level-easy';
            break;
        case 'Normal':
            className = 'level-normal';
            break;
        case 'Hard':
            className = 'level-hard';
            break;
        default:
            levelText = 'Unknown';
    }

    var displayElement = document.getElementById('levelDisplay');
    if (displayElement) {
        displayElement.textContent = levelText;
        displayElement.classList.add(className);
    }
}