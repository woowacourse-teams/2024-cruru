const MSW_ACTIVE_ID = 'mswActive';

export default async function settingMock() {
  if (process.env.NODE_ENV === 'development' && window.localStorage.getItem(MSW_ACTIVE_ID)) {
    const worker = await import('@mocks/browser');
    await worker.default.start();
  }

  if (process.env.NODE_ENV === 'development') {
    const body = document.querySelector('body');
    const floatingMSWActiveButton = document.createElement('button');

    Object.assign(floatingMSWActiveButton.style, {
      position: 'fixed',
      bottom: '20px',
      right: '80px',
      padding: '10px 15px',
      backgroundColor: '#007bff',
      color: 'white',
      border: 'none',
      borderRadius: '5px',
      cursor: 'pointer',
      zIndex: '9999',
    });

    const currentValue = window.localStorage.getItem(MSW_ACTIVE_ID);
    floatingMSWActiveButton.textContent = `MSW ${currentValue ? 'ON' : 'OFF'}`;

    floatingMSWActiveButton.addEventListener('click', () => {
      if (currentValue) {
        window.localStorage.removeItem(MSW_ACTIVE_ID);
      } else {
        window.localStorage.setItem(MSW_ACTIVE_ID, 'true');
      }
      window.location.reload();
    });

    body?.appendChild(floatingMSWActiveButton);
  }
}
