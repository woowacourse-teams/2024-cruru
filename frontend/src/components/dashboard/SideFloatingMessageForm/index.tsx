import { useSpecificApplicantId } from '@contexts/SpecificApplicnatIdContext';
import { useFloatingEmailForm } from '@contexts/FloatingEmailFormContext';
import { useModal } from '@contexts/ModalContext';
import { useParams } from 'react-router-dom';
import useProcess from '@hooks/useProcess';

import MessageForm, { SubmitProps } from './MessageForm';
import S from './style';

export default function SideFloatingMessageForm() {
  const { isOpen, close } = useFloatingEmailForm();
  const { applicantId } = useSpecificApplicantId();
  const { isOpen: isModalOpen } = useModal();
  const { dashboardId, applyFormId } = useParams() as { dashboardId: string; applyFormId: string };
  const { processes } = useProcess({ dashboardId, applyFormId });

  const findApplicantName = processes
    .flatMap((process) => process.applicants)
    .find((applicant) => applicant.applicantId === applicantId)?.applicantName;

  const handleSubmit = (props: SubmitProps) => {
    // TODO:  Post요청을 보내야 합니다.
    console.log(props);
    console.log(applicantId);
    console.log(isModalOpen);
  };

  return (
    <S.SideFloatingContainer>
      {isOpen && findApplicantName && (
        <MessageForm
          recipient={findApplicantName}
          onSubmit={handleSubmit}
          onClose={close}
        />
      )}
    </S.SideFloatingContainer>
  );
}
