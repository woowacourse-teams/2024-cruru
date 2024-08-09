export default function createSimpleKey(input: string): string {
  const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
  const inputHash = input.split('').reduce((acc, char) => acc + char.charCodeAt(0), 0);

  return Array.from({ length: 8 }).reduce((id: string, _, index) => {
    const randomIndex = (inputHash + index) % characters.length;
    return id + randomIndex;
  }, '');
}
