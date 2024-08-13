import { useParams } from 'react-router-dom';

import Dropdown from '@components/common/Dropdown';
import Button from '@components/common/Button';
import useProcess from '@hooks/useProcess';
import useApplicant from '@hooks/useApplicant';
import specificApplicant from '@hooks/useSpecificApplicant';
import formatDate from '@utils/formatDate';
import { useModal } from '@contexts/ModalContext';
import S from './style';

interface ApplicantBaseInfoProps {
  applicantId: number;
}

export default function ApplicantBaseInfo({ applicantId }: ApplicantBaseInfoProps) {
  const { dashboardId, postId } = useParams() as { dashboardId: string; postId: string };
  const { mutate: rejectMutate } = specificApplicant.useRejectApplicant({ dashboardId, postId });
  const { processList, isLoading: isProcessLoading } = useProcess({ dashboardId, postId });

  const { data: applicantBaseInfo, isLoading: isBaseInfoLoading } = specificApplicant.useGetBaseInfo({ applicantId });
  const { moveApplicantProcess } = useApplicant({ applicantId });
  const { close } = useModal();

  if (!applicantBaseInfo) {
    return <div>no data</div>; // TODO: 핸들링
  }

  if (isBaseInfoLoading || isProcessLoading) {
    return <div>Loading...</div>; // TODO: Loading 핸들링
  }

  const { applicant, process } = applicantBaseInfo;

  const items = processList
    .filter(({ processName }) => processName !== process.name)
    .map(({ processId, processName }) => ({
      id: processId,
      name: processName,
      onClick: ({ targetProcessId }: { targetProcessId: number }) => {
        moveApplicantProcess.mutate({ processId: targetProcessId, applicants: [applicantId] });
      },
    }));

  const rejectAppHandler = () => {
    const isConfirmed = window.confirm('정말 해당 지원자를 불합격 하시겠습니까? 불합격은 번복할 수 없습니다.');

    if (isConfirmed) {
      rejectMutate({ applicantId });
      close();
    }
  };

  return (
    <S.Container>
      <S.Title>{applicant.name}</S.Title>
      <S.ActionRow>
        <Dropdown
          initValue={process.name}
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
          <S.Value>{applicant.email}</S.Value>
        </S.DetailRow>
        <S.DetailRow>
          <S.Label>연락처</S.Label>
          <S.Value>{applicant.phone}</S.Value>
        </S.DetailRow>
        <S.DetailRow>
          <S.Label>접수일</S.Label>
          <S.Value>{formatDate(applicant.createdAt)}</S.Value>
        </S.DetailRow>
      </S.DetailContainer>
    </S.Container>
  );
}
