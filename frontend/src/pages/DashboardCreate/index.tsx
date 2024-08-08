import RecruitmentSidebar from '@components/recruitment/RecruitmentSidebar';
import useDashboardCreateForm from '@hooks/useDashboardCreateForm';
import S from './style';

export default function DashboardCreate() {
  useDashboardCreateForm(); // TODO: stepState를 통해 단계별 렌더링

  return (
    <S.Layout>
      <RecruitmentSidebar />

      <S.MainContainer>
        Create!!!!
        {/*
        TODO: 단계별 컴포넌트를 렌더링합니다.
        1. Recrutement
        2. Apply
        3. Finish
      */}
      </S.MainContainer>
    </S.Layout>
  );
}
