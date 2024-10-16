function checkElementPosition(element?: HTMLElement | null) {
  if (!element) return { isRight: false, isBottom: false };

  const rect = element.getBoundingClientRect();
  const windowWidth = window.innerWidth;
  const windowHeight = window.innerHeight;

  const isRight = rect.right > windowWidth * 0.8;
  const isBottom = rect.bottom > windowHeight * 0.8;

  return { isRight, isBottom };
}

export default checkElementPosition;
