import Dropdown from '@components/common/Dropdown';
import Button from '@components/common/Button';
import useProcess from '@hooks/useProcess';
import useApplicant from '@hooks/useApplicant';
import useSpecificApplicant from '@hooks/useSpecificApplicant';
import formatDate from '@utils/formatDate';
import { useModal } from '@contexts/ModalContext';
import S from './style';

interface ApplicantBaseDetailProps {
  applicantId: number;
}

export default function ApplicantBaseDetail({ applicantId }: ApplicantBaseDetailProps) {
  const { data: applicantBaseDetail } = useSpecificApplicant({ applicantId });
  const { processList } = useProcess();
  const { moveApplicantProcess, rejectApplicant } = useApplicant({ applicantId });
  const { close } = useModal();

  if (!applicantBaseDetail) {
    return <div>no data</div>; // TODO: 핸들링
  }

  const items = processList
    .filter(({ processName }) => processName !== applicantBaseDetail.processName)
    .map(({ processId, processName }) => ({
      id: processId,
      name: processName,
      onClick: ({ targetProcessId }: { targetProcessId: number }) => {
        moveApplicantProcess.mutate({ processId: targetProcessId, applicants: [applicantId] });
      },
    }));

  const rejectAppHandler = () => {
    rejectApplicant.mutate({ applicantId });
    close();
  };

  return (
    <S.Container>
      <S.Title>{applicantBaseDetail.name}</S.Title>
      <S.ActionRow>
        <Dropdown
          initValue={applicantBaseDetail.processName}
          size="sm"
          items={items}
          width={112}
          isShadow={false}
        />
        <Button
          size="sm"
          color="error"
          onClick={rejectAppHandler}
        >
          불합격
        </Button>
      </S.ActionRow>
      <S.DetailContainer>
        <S.DetailRow>
          <S.Label>이메일</S.Label>
          <S.Value>{applicantBaseDetail.email}</S.Value>
        </S.DetailRow>
        <S.DetailRow>
          <S.Label>연락처</S.Label>
          <S.Value>{applicantBaseDetail.phone}</S.Value>
        </S.DetailRow>
        <S.DetailRow>
          <S.Label>접수일</S.Label>
          <S.Value>{formatDate(applicantBaseDetail.createdAt)}</S.Value>
        </S.DetailRow>
      </S.DetailContainer>
    </S.Container>
  );
}
