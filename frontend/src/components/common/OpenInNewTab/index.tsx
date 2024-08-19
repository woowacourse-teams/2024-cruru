import { Link } from 'react-router-dom';
import { HiExternalLink } from 'react-icons/hi';

import S from './style';

interface OpenInNewTabProps {
  url: string;
  title: string;
}

export default function OpenInNewTab({ url, title }: OpenInNewTabProps) {
  return (
    <S.Container>
      <HiExternalLink />
      <Link
        to={url}
        target="_blank"
        rel="noreferrer"
      >
        <S.Title>{title}</S.Title>
      </Link>
    </S.Container>
  );
}
