import KanbanBoard from '@components/KanbanBoard';
import ProcessNavBar from '@components/ProcessNavBar';
import useProcess from '../../hooks/useProcess';
import S from './style';

export default function ApplicantManage() {
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
      <KanbanBoard processes={processes} />
    </S.AppContainer>
  );
}
