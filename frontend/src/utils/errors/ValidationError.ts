class ValidationError extends Error {
  public inputName: string;

  constructor({ message, inputName }: { message: string; inputName: string }) {
    super(message);
    this.inputName = inputName;
  }
}

export default ValidationError;
