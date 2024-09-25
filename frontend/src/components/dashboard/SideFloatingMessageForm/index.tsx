import { useSpecificApplicantId } from '@contexts/SpecificApplicnatIdContext';
import { useFloatingEmailForm } from '@contexts/FloatingEmailFormContext';
import { useModal } from '@contexts/ModalContext';
import MessageForm, { SubmitProps } from './MessageForm';
import S from './style';

export default function SideFloatingMessageForm() {
  const { isOpen, close } = useFloatingEmailForm();
  const { applicantId } = useSpecificApplicantId();
  const { isOpen: isModalOpen } = useModal();

  const handleSubmit = (props: SubmitProps) => {
    // TODO:  Post요청을 보내야 합니다.
    console.log(props);
    console.log(applicantId);
    console.log(isModalOpen);
  };

  return (
    <S.SideFloatingContainer>
      {isOpen && (
        <MessageForm
          recipient="lurgi"
          onSubmit={handleSubmit}
          onClose={close}
        />
      )}
    </S.SideFloatingContainer>
  );
}
