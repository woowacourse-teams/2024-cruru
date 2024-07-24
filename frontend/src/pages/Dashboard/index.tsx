import { SpecificApplicantIdProvider } from '@contexts/SpecificApplicnatIdContext';
import KanbanBoard from '@components/dashboard/KanbanBoard';
import ProcessNavBar from '@components/dashboard/ProcessNavBar';
import useProcess from '@hooks/useProcess';

import S from './style';

export default function Dashboard() {
  const { processes, isLoading, error } = useProcess();

  if (isLoading) {
    // TODO: Loading 핸들링
    return <div>Loading...</div>;
  }

  if (error) {
    // TODO: Error 핸들링
    return <div>Error</div>;
  }

  return (
    <S.AppContainer>
      <ProcessNavBar currentMenuKey="applicant" />

      <SpecificApplicantIdProvider>
        <KanbanBoard processes={processes} />
      </SpecificApplicantIdProvider>
    </S.AppContainer>
  );
}
