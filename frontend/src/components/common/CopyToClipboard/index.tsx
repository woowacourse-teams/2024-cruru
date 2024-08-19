import useCopyToClipboard from '@hooks/utils/useCopyToClipboard';

import Button from '../Button';
import S from './style';

interface CopyLinkClipboardProps {
  url: string;
}

export default function CopyToClipboard({ url }: CopyLinkClipboardProps) {
  const { copyToClipboard } = useCopyToClipboard({ url });

  return (
    <Button
      size="sm"
      color="primary"
      type="button"
      onClick={copyToClipboard}
    >
      <S.Label>Copy</S.Label>
    </Button>
  );
}
