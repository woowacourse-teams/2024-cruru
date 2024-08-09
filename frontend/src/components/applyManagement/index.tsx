import { useParams } from 'react-router-dom';
import useApplyManagement from '@hooks/useApplyManagement';
import Apply from '@components/dashboard/DashboardCreate/Apply';

export default function ApplyManagement() {
  const { postId } = useParams<{ postId: string }>() as {
    postId: string;
  };

  const {
    applyState,
    addQuestion,
    setQuestionTitle,
    setQuestionType,
    setQuestionOptions,
    setQuestionRequiredToggle,
    setQuestionPrev,
    setQuestionNext,
    deleteQuestion,
  } = useApplyManagement({ postId });

  return (
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
      prevStep={() => {}}
      nextStep={() => alert('업데이트 되었습니다!')}
      isModify
    />
  );
}
