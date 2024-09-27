import Apply from '@components/dashboard/DashboardCreate/Apply';
import Finish from '@components/dashboard/DashboardCreate/Finish';
import Recruitment from '@components/dashboard/DashboardCreate/Recruitment';
import RecruitmentSidebar from '@components/recruitment/RecruitmentSidebar';
import useDashboardCreateForm from '@hooks/useDashboardCreateForm';

import S from './style';

export default function DashboardCreate() {
  const {
    stepState,
    prevStep,
    nextStep,

    recruitmentInfoState,
    setRecruitmentInfoState,
    applyState,

    addQuestion,
    setQuestionTitle,
    setQuestionType,
    setQuestionOptions,
    setQuestionRequiredToggle,
    setQuestionPrev,
    setQuestionNext,
    deleteQuestion,

    finishResJson,
  } = useDashboardCreateForm();

  const isRecruitmentStep = stepState === 'recruitmentForm';
  const isApplyStep = stepState === 'applyForm';
  const isFinish = stepState === 'finished';

  return (
    <S.Layout>
      <RecruitmentSidebar
        options={[
          { text: '공고 작성', isSelected: isRecruitmentStep },
          { text: '지원서 작성', isSelected: stepState === 'applyForm' },
          { text: '공고 게시', isSelected: isFinish },
        ]}
      />

      <S.MainContainer>
        {isRecruitmentStep && (
          <Recruitment
            recruitmentInfoState={recruitmentInfoState}
            setRecruitmentInfoState={setRecruitmentInfoState}
            nextStep={nextStep}
          />
        )}

        {isApplyStep && (
          <Apply
            applyState={applyState}
            addQuestion={addQuestion}
            setQuestionTitle={setQuestionTitle}
            setQuestionType={setQuestionType}
            setQuestionOptions={setQuestionOptions}
            setQuestionRequiredToggle={setQuestionRequiredToggle}
            setQuestionPrev={setQuestionPrev}
            setQuestionNext={setQuestionNext}
            deleteQuestion={deleteQuestion}
            prevStep={prevStep}
            nextStep={nextStep}
          />
        )}

        {finishResJson && (
          <Finish
            applyFormId={finishResJson.applyFormId}
            dashboardId={finishResJson.dashboardId}
          />
        )}
      </S.MainContainer>
    </S.Layout>
  );
}
