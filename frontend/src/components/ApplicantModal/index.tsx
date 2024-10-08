import { useSpecificApplicantId } from '@contexts/SpecificApplicnatIdContext';
import { useSpecificProcessId } from '@contexts/SpecificProcessIdContext';

import BaseModal from './BaseModal';

import ApplicantBaseInfo from './ApplicantBaseInfo';
import ApplicatnModalHeader from './ModalHeader';
import QuestionSection from './ApplicantDetailInfo/QuestionSection';
import ApplicantEvalInfo from './ApplicantEvalInfo';
import EvaluationHeader from './ApplicantEvalInfo/EvaluationHeader';

import S from './style';

export default function ApplicantModal() {
  const { applicantId } = useSpecificApplicantId();
  const { processId } = useSpecificProcessId();
  if (!applicantId || !processId) return null;

  return (
    <BaseModal>
      <S.Container>
        <S.ModalHeader>
          <ApplicatnModalHeader title="지원서" />
        </S.ModalHeader>

        <S.ModalSidebar>
          <ApplicantBaseInfo applicantId={applicantId} />
        </S.ModalSidebar>

        <S.ModalNav>
          <S.ModalNavHeaderContainer>
            <S.ModalNavHeader>지원서</S.ModalNavHeader>
            <S.ModalNavContent>지원 시 접수된 지원서 내용입니다.</S.ModalNavContent>
          </S.ModalNavHeaderContainer>
        </S.ModalNav>

        <S.ModalMain>
          <QuestionSection applicantId={applicantId} />
        </S.ModalMain>

        <S.ModalEvalHeader>
          <EvaluationHeader
            title="지원자 평가"
            description="지원자 평가에 대한 내용입니다."
          />
        </S.ModalEvalHeader>

        <S.ModalAside>
          <ApplicantEvalInfo
            applicantId={applicantId}
            processId={processId}
          />
        </S.ModalAside>
      </S.Container>
    </BaseModal>
  );
}
