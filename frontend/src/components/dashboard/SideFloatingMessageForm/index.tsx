import { useSpecificApplicantId } from '@contexts/SpecificApplicnatIdContext';
import { useMultiApplicant } from '@contexts/MultiApplicantContext';
import { useFloatingEmailForm } from '@contexts/FloatingEmailFormContext';
import { useParams } from 'react-router-dom';
import useProcess from '@hooks/useProcess';
import useEmail from '@hooks/useEmail';

import MessageForm, { SubmitProps } from './MessageForm';
import S from './style';

export default function SideFloatingMessageForm() {
  const { isOpen, close } = useFloatingEmailForm();
  const { applicantId } = useSpecificApplicantId();
  const { applicants: applicantIds } = useMultiApplicant();
  const { dashboardId, applyFormId } = useParams() as {
    dashboardId: string;
    applyFormId: string;
  };
  const { processes } = useProcess({ dashboardId, applyFormId });
  const { mutate: sendMutate, isPending } = useEmail();
  const clubId = localStorage.getItem('clubId');

  const targetApplicantIds =
    applicantIds && applicantIds.length > 0 ? applicantIds : applicantId ? [applicantId] : null;

  if (!clubId || !targetApplicantIds || targetApplicantIds.length === 0) return null;

  const findApplicantName = (id: number) =>
    processes.flatMap((process) => process.applicants).find((applicant) => applicant.applicantId === id)?.applicantName;

  const getRecipientString = () => {
    if (targetApplicantIds && targetApplicantIds.length > 1) {
      const firstApplicantName = findApplicantName(targetApplicantIds[0]);
      return `${firstApplicantName} 포함 ${targetApplicantIds.length}명`;
    }

    if (targetApplicantIds && targetApplicantIds.length === 1) {
      return findApplicantName(targetApplicantIds[0]);
    }

    if (applicantId) {
      return findApplicantName(applicantId);
    }
  };

  const recipientString = getRecipientString();

  const handleSubmit = (props: SubmitProps) => {
    if (!isPending) sendMutate({ clubId, applicantIds: targetApplicantIds, ...props }, { onSuccess: close });
  };

  return (
    <S.SideFloatingContainer>
      {isOpen && recipientString && (
        <MessageForm
          recipient={recipientString}
          onSubmit={handleSubmit}
          onClose={close}
          isPending={isPending}
        />
      )}
    </S.SideFloatingContainer>
  );
}
