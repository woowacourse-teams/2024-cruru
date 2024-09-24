import { PageLoadingSpinner } from '@components/_common/atoms/Spinner';
import S from './style';

export default function LoadingPage() {
  return (
    <S.PageLayout>
      <PageLoadingSpinner />
    </S.PageLayout>
  );
}
