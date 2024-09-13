import { Link } from 'react-router-dom';
import { HiClipboard, HiClipboardCheck, HiExternalLink } from 'react-icons/hi';

import IconButton from '@components/_common/atoms/IconButton';
import useCopyToClipboard from '@hooks/utils/useCopyToClipboard';
import S from './style';

interface CopyLinkClipboardProps {
  url: string;
}

export default function SharePost({ url }: CopyLinkClipboardProps) {
  const { isCopied, copyToClipboard } = useCopyToClipboard({ url });

  return (
    <S.Wrapper>
      <S.LinkContainer>
        <S.Link>
          <Link
            to={url}
            target="_blank"
            rel="noreferrer"
          >
            <HiExternalLink />
            {url}
          </Link>
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
