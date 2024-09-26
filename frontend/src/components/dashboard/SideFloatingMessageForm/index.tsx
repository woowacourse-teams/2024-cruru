import { useSpecificApplicantId } from '@contexts/SpecificApplicnatIdContext';
import { useFloatingEmailForm } from '@contexts/FloatingEmailFormContext';
import { useParams } from 'react-router-dom';
import useProcess from '@hooks/useProcess';
import useEmail from '@hooks/useEmail';

import MessageForm, { SubmitProps } from './MessageForm';
import S from './style';

export default function SideFloatingMessageForm() {
  const { isOpen, close } = useFloatingEmailForm();
  const { applicantId } = useSpecificApplicantId();
  const { dashboardId, applyFormId } = useParams() as {
    dashboardId: string;
    applyFormId: string;
  };
  const { processes } = useProcess({ dashboardId, applyFormId });
  const { mutate: sendMutate, isPending } = useEmail();
  const clubId = localStorage.getItem('clubId');

  if (!applicantId || !clubId) return null;

  const findApplicantName = processes
    .flatMap((process) => process.applicants)
    .find((applicant) => applicant.applicantId === applicantId)?.applicantName;

  const handleSubmit = (props: SubmitProps) => {
    if (!isPending) sendMutate({ clubId, applicantId, ...props }, { onSuccess: close });
  };

  return (
    <S.SideFloatingContainer>
      {isOpen && findApplicantName && (
        <MessageForm
          recipient={findApplicantName}
          onSubmit={handleSubmit}
          onClose={close}
          isPending={isPending}
        />
      )}
    </S.SideFloatingContainer>
  );
}
