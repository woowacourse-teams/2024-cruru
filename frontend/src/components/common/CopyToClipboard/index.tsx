import useCopyToClipboard from '@hooks/utils/useCopyToClipboard';
import { HiClipboard, HiClipboardCheck } from 'react-icons/hi';
import IconButton from '../IconButton';
import S from './style';

interface CopyLinkClipboardProps {
  url: string;
}

export default function CopyToClipboard({ url }: CopyLinkClipboardProps) {
  const { isCopied, copyToClipboard } = useCopyToClipboard({ url });

  return (
    <S.Wrapper>
      <S.LinkContainer>
        <S.Link
          href={url}
          target="_blank"
          rel="noreferrer"
        >
          {url}
        </S.Link>
      </S.LinkContainer>

      <IconButton
        outline={false}
        shape="square"
        type="button"
        onClick={copyToClipboard}
      >
        {isCopied ? <HiClipboardCheck /> : <HiClipboard />}
      </IconButton>
    </S.Wrapper>
  );
}
