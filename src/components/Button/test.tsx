import { render, screen } from "@testing-library/react";

import Button from "./";

test("renders the Button component with correct text", () => {
  const buttonText = "렛서 판다 붐은 온다";
  render(<Button />);

  const buttonElement = screen.getByText(buttonText);
  expect(buttonElement).toBeInTheDocument();
});
